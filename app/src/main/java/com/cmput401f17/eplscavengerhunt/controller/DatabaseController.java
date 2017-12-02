package com.cmput401f17.eplscavengerhunt.controller;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.cmput401f17.eplscavengerhunt.model.MultipleChoiceQuestion;
import com.cmput401f17.eplscavengerhunt.model.PicInputQuestion;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;
import com.cmput401f17.eplscavengerhunt.model.WrittenInputQuestion;
import com.cmput401f17.eplscavengerhunt.model.Zone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DatabaseController handles retrieving the branch, zones and questions.
 */
public class DatabaseController {

    public DatabaseController() {
    }

    /**
     * Retrieves all zones belonging to the library branch
     */
    public List<Zone> retrieveZones(final String branch) {
        List<Zone> zones = new ArrayList<>();

        Zone zone = new Zone();
        zone.setBranch(branch);
        final String path = "http://162.246.156.95:5000/getZone";
        // alternative path for local testing: http://localhost:5000/getQuestion
        taskParams responseParams = new taskParams(zone, null, path, null);
        try {
            System.out.println(responseParams.zone.getBranch());
            zones = new GetZone().execute(responseParams).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return zones;
    }

    /**
     * Retrieves all questions belonging to the zone.
     */
    public List<Question> retrieveQuestionsInZone(final Zone zone) {
        List<Question> testList = new ArrayList<>();
        final String path = "http://162.246.156.95:5000/getQuestion";
        // alternative path for local testing: http://localhost:5000/getQuestion
        taskParams responseParams = new taskParams(zone, null, path, null);
        try {
            testList = new GetQuestion().execute(responseParams).get();
            Log.i("@@@DatabaseController", testList.toString());
            String tempZoneName = zone.getName();
            zone.setName(tempZoneName.replace("_", " "));
            return testList;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return testList;
    }

    /**
     * Adds the users response to the data store
     */
    public void updateAnalyticsForQuestion(final Question question, final Response response) {
        final String path = "http://162.246.156.95:5000/updateAnalytics";
        // alternative path for local testing: http://localhost:5000/getQuestion
        taskParams responseParams = new taskParams(null, question, path, response);
        new UpdateAnalytics().execute(responseParams);
    }

    public class taskParams {
        // Note: zone will be used in both apps.
        //       question will be used in web app.
        Zone zone;
        Question question;
        String url;
        Response response;

        taskParams(Zone zone, Question question, String url, Response response) {
            this.zone = zone;
            this.question = question;
            this.url = url;
            this.response = response;
        }

    }

    public class GetZone extends AsyncTask<taskParams, List<Zone>, List<Zone>> {

        @Override
        protected List<Zone> doInBackground(taskParams... params) {
            List<Zone> zones = new ArrayList<>();
            HttpURLConnection c = null;
            taskParams taskInfo = params[0];
            try {
                // url must point to flask endpoint. "http://162.246.156.95:5000/..."
                String restUrl = taskInfo.url + "/" + taskInfo.zone.getBranch();
                URL u = new URL(restUrl);

                c = (HttpURLConnection) u.openConnection();

                // tells the api to GET something, and gives it variables it will need.
                c.setRequestMethod("GET");
                //c.setRequestProperty("branch", taskInfo.zone.getBranch());

                c.setUseCaches(false);
                c.setAllowUserInteraction(false);

                // if it lags for 5 seconds while connecting, backout.
                c.setConnectTimeout(50000);
                c.setReadTimeout(50000);
                // connect!
                c.connect();

                // After it connects, the api does its thing, and
                // this will resume when it retrieves data.

                // This gets the connection status of the api.
                int status = c.getResponseCode();

                switch (status) {
                    case 200:
                        // reads what api returned, converts it into json format
                        JsonReader jsonReader = new JsonReader(new BufferedReader(new InputStreamReader(c.getInputStream())));
                        Log.d("dbcontroller!!!", jsonReader.toString());
                        try {
                            //questions =  jsonQuestionArray(jsonReader);
                            zones = jsonZoneArray(jsonReader);

                        } finally {
                            jsonReader.close();
                        }
                }
            } catch (MalformedURLException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            } catch (IOException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            } finally {
                if (c != null) {
                    try {
                        c.disconnect();
                    } catch (Exception ex) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            return zones;
        }

        @Override
        protected void onPostExecute(List<Zone> q) {
            super.onPostExecute(q);
        }

        private List<Zone> jsonZoneArray(JsonReader jsonReader) throws IOException {
            List<Zone> zones = new ArrayList<>();
            jsonReader.beginObject();

            while (jsonReader.hasNext()) {
                String key = jsonReader.nextName();
                if (key.equals("data")) {
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        zones.add(readZone(jsonReader));
                    }
                    jsonReader.endArray();
                } else {
                    jsonReader.skipValue();
                }
            }

            jsonReader.endObject();
            return zones;
        }

        private Zone readZone(JsonReader jsonReader) throws IOException {
            String key;
            Zone zone = new Zone();
            jsonReader.beginObject(); // start reading each sql row entry
            while (jsonReader.hasNext()) {
                key = jsonReader.nextName();
                if (key.equals("beaconID")) {
                    zone.setBeaconID(jsonReader.nextString());
                } else if (key.equals("zone")) {
                    zone.setName(jsonReader.nextString());
                } else if (key.equals("branch")) {
                    zone.setBranch(jsonReader.nextString());
                } else if (key.equals("category")) {
                    zone.setCategory(jsonReader.nextString());
                } else if (key.equals("color")) {
                    zone.setColor(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                }
            }
            jsonReader.endObject();
            return zone;
        }
    }

    public class GetQuestion extends AsyncTask<taskParams, List<Question>, List<Question>> {

        @Override
        protected List<Question> doInBackground(taskParams... params) {
            List<Question> questions = new ArrayList<>();
            HttpURLConnection c = null;
            taskParams taskInfo = params[0];
            try {
                // url must point to flask endpoint. "http://162.246.156.95:5000/..."
                String restUrl = taskInfo.url + "/" + taskInfo.zone.getName() + "/" + taskInfo.zone.getBranch();
                URL u = new URL(restUrl);
                System.out.println(restUrl);
                c = (HttpURLConnection) u.openConnection();

                // tells the api to GET something,
                // and gives it variables it will need.
                //c.setRequestMethod("GET");
                //c.setRequestProperty("zone", taskInfo.zone.getName());
                //c.setRequestProperty("branch", taskInfo.zone.getBranch());

                c.setUseCaches(false);
                c.setAllowUserInteraction(false);

                // if it lags for 5 seconds while connecting, backout.
                c.setConnectTimeout(50000);
                c.setReadTimeout(50000);
                // connect!
                c.connect();

                // After it connects, the api does its thing, and
                // this will resume when it retrieves data.

                // This gets the connection status of the api.
                int status = c.getResponseCode();
                System.out.println(status);

                switch (status) {
                    case 200:
                        // reads what api returned, converts it into json format
                        JsonReader jsonReader = new JsonReader(new BufferedReader(new InputStreamReader(c.getInputStream())));
                        try {
                            questions = jsonQuestionArray(jsonReader);

                        } finally {
                            jsonReader.close();
                        }
                }
            } catch (MalformedURLException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            } catch (IOException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            } finally {
                if (c != null) {
                    try {
                        c.disconnect();
                    } catch (Exception ex) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            return questions;
        }

        @Override
        protected void onPostExecute(List<Question> q) {
            super.onPostExecute(q);
        }

        private List<Question> jsonQuestionArray(JsonReader jsonReader) throws IOException {
            List<Question> questions = new ArrayList<>();
            jsonReader.beginObject();

            while (jsonReader.hasNext()) {
                String key = jsonReader.nextName();
                if (key.equals("data")) {
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        questions.add(readQuestion(jsonReader));
                    }
                    jsonReader.endArray();
                } else {
                    jsonReader.skipValue();
                }
            }

            jsonReader.endObject();
            return questions;
        }

        private Question readQuestion(JsonReader jsonReader) throws IOException {
            String key;
            int questionID = 0;
            String prompt = "";
            String answer = "";
            String zone = "";
            String branch = "";
            String type = ""; // the type of input for question.
            String iLink = "";
            String sLink = "";
            String blanks = "";
            List<String> choiceList = null;
            Question question;
            // Question question = new Question();

            // initial read to figure out question type
            jsonReader.beginObject(); // start reading each sql row entry
            while (jsonReader.hasNext()) {
                key = jsonReader.nextName();
                if (key.equals("Prompt")) {
                    prompt = jsonReader.nextString();
                } else if (key.equals("Choices")) {
                    String choices = jsonReader.nextString();
                    choiceList = new ArrayList<>(Arrays.asList(choices.split("\\|_\\|")));
                } else if (key.equals("Solution")) {
                    answer = jsonReader.nextString();
                } else if (key.equals("zone")) {
                    zone = jsonReader.nextString();
                } else if (key.equals("branch")) {
                    branch = jsonReader.nextString();
                } else if (key.equals("qType")) {
                    type = jsonReader.nextString();
                } else if (key.equals("iLink")) {
                    iLink = jsonReader.nextString();
                } else if (key.equals("sLink")) {
                    sLink = jsonReader.nextString();
                } else if (key.equals("id")) {
                    questionID = jsonReader.nextInt();
                } else if (key.equals("blanks")) {
                    blanks = jsonReader.nextString();
                } else {
                    jsonReader.skipValue();
                }
            }
            jsonReader.endObject();

            if (type.equals("writInput")) {
                question = new WrittenInputQuestion(questionID, prompt, iLink, answer);
                question.setZone(zone);
                question.setBranch(branch);
                question.setChoices(choiceList);
                question.setSoundLink(sLink);
                question.setBlanks(blanks);
            } else if (type.equals("picInput")) {
                question = new PicInputQuestion(questionID, prompt, iLink, choiceList, answer);
                question.setSoundLink(sLink);
                question.setZone(zone);
                question.setBranch(branch);
            } else {
                question = new MultipleChoiceQuestion(questionID, prompt, iLink, choiceList, answer);
                question.setZone(zone);
                question.setBranch(branch);
                question.setSoundLink(sLink);
            }
            // after we figure out the question type, instantiate
            // the correct model. then re-parse the

            return question;
        }
    }

    public class UpdateAnalytics extends AsyncTask<taskParams, Void, Void> {
        @Override
        protected Void doInBackground(taskParams... params) {
            HttpURLConnection c = null;
            taskParams taskInfo = params[0];
            try {
                // url must point to flask endpoint. "http://162.246.156.95:5000/..."
                String restUrl = taskInfo.url + "/" + taskInfo.question.getQuestionID() + "/" + taskInfo.response.getResponseStr();
                URL u = new URL(restUrl);
                c = (HttpURLConnection) u.openConnection();

                // tells the api to GET something,
                // and gives it variables it will need.
                c.setRequestMethod("PUT");

                c.setUseCaches(false);
                c.setAllowUserInteraction(false);

                // if it lags for 5 seconds while connecting, backout.
                c.setConnectTimeout(5000);
                c.setReadTimeout(5000);
                // connect!
                c.connect();

                // After it connects, the api does its thing, and
                // this will resume when it retrieves data.

                // This gets the connection status of the api.
                int status = c.getResponseCode();
            } catch (MalformedURLException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            } catch (IOException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            } finally {
                if (c != null) {
                    try {
                        c.disconnect();
                    } catch (Exception ex) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            return null;
        }
    }
}
