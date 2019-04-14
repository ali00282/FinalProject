package com.example.finalproject.dictionary;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalproject.R;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Dict_DetailFragment extends Fragment {

    private boolean isTablet =false;
    private Bundle dataFromActivity;
    private long id;
    private String wordToSearch;
    TextView word, pronunciation, definition;
    String definitions = "";
    private Button saveButton, deleteButton;

    DictDBHelper dbOpener;
    SQLiteDatabase db;
    public void setTablet(boolean tablet) {
        System.out.println("test");
        isTablet = tablet;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataFromActivity = getArguments();
        wordToSearch = dataFromActivity.getString("wordSearch");

        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.dict_definitiondetail, container, false);

        word = (TextView) result.findViewById(R.id.word);
        pronunciation = (TextView) result.findViewById(R.id.pronunciation);
        definition = (TextView) result.findViewById(R.id.definitions);
        saveButton = (Button)result.findViewById(R.id.saveButton);
        deleteButton = (Button)result.findViewById(R.id.deleteButton);
        dbOpener = new DictDBHelper(getActivity());
        db = dbOpener.getWritableDatabase();

        if (wordToSearch != null) {
            DataFetcher networkThread = new DataFetcher();
            networkThread.execute("https://www.dictionaryapi.com/api/v1/references/sd3/xml/" + wordToSearch + "?key=4556541c-b8ed-4674-9620-b6cba447184f"); //this starts doInBackground on other thread
            isSavedWord(false);
        } else {
            isSavedWord(true);
            word.setText(dataFromActivity.getString("wordSave"));
            pronunciation.setText(dataFromActivity.getString("pronunciationSave"));
            definition.setText(dataFromActivity.getString("definitionsSave"));
            id = dataFromActivity.getLong("idSave");
            deleteButton.setOnClickListener( clk -> {
                System.out.println("delete btn");
                if(isTablet) { //both the list and details are on the screen:
                    System.out.println("is table");
                    Dict_MainActivity parent = (Dict_MainActivity)getActivity();
                    parent.deleteWord(id); //this deletes the item and updates the list

                    isSavedWord(false);
                    //now remove the fragment since you deleted it from the database:
                    // this is the object to be removed, so remove(this):
                }
                //for Phone:
                else //You are only looking at the details, you need to go back to the previous list page
                {
                    Dict_EmptyActivity parent = (Dict_EmptyActivity) getActivity();
                    Intent backToFragmentExample = new Intent();
                    backToFragmentExample.putExtra("wordID", id);

                    parent.setResult(Activity.RESULT_OK, backToFragmentExample); //send data back to FragmentExample in onActivityResult()
                    parent.finish(); //go back
                }
            });
            saveButton.setOnClickListener(click -> {
                if (isTablet) {
                    Dict_MainActivity parent = (Dict_MainActivity)getActivity();
                    id = parent.insertWord(word.getText().toString(), pronunciation.getText().toString(), definitions);
                }else {
                    Dict_EmptyActivity parent = (Dict_EmptyActivity)getActivity();
                    Intent backToFragmentExample = new Intent();
                    backToFragmentExample.putExtra("word", word.getText().toString());
                    backToFragmentExample.putExtra("pronunciation", pronunciation.getText().toString());
                    backToFragmentExample.putExtra("definitions", definitions);

                    parent.setResult(35, backToFragmentExample); //send data back to FragmentExample in onActivityResult()
                    parent.finish(); //go back
                }
                if (id > 0)
                    isSavedWord(true);

            });
        }



        return result;
    }

    // a subclass of AsyncTask                  Type1    Type2    Type3
    private class DataFetcher extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {


            try {
                //get the string url:
                String myUrl = params[0];

                //create the network connection:
                URL url = new URL(myUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inStream = urlConnection.getInputStream();


                //create a pull parser:
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(inStream, "UTF-8");  //inStream comes from line 46

                int entryIndex = 0;
                int numOfDefitions = 0;

                //now loop over the XML:
                while (xpp.next() != XmlPullParser.END_DOCUMENT) {
                    if (xpp.next() == XmlPullParser.START_TAG) {
                        String tagName = xpp.getName();

                        if (tagName.equals("entry")) {
                            entryIndex++;
                            if (entryIndex > 1)
                                break;
                            if (xpp.getAttributeValue(null, "id").contains("[")) {
                                int index = xpp.getAttributeValue(null, "id").indexOf("[");
                                word.setText(xpp.getAttributeValue(null, "id").substring(0, index));

                            } else {
                                word.setText(xpp.getAttributeValue(null, "id"));
                            }

                        }
                        if (tagName.equals("pr")) {
                            pronunciation.setText("Pronunciation: "+xpp.nextText());

                        }
                        if (tagName.equals("dt")){
                            if (numOfDefitions > 2){
                                break;
                            }
                            String xmlInner = getInnerXml(xpp);
                            if (xmlInner.contains("<")){
                                xmlInner = xmlInner.substring(0, xmlInner.indexOf("<"));
                            }
                            definitions = definitions + "\n"+xmlInner;
                            numOfDefitions++;

                        }
                    }

                }

            } catch (Exception ex) {
                Log.e("Crash!!", ex.getMessage());
            }

            //return type 3, which is String:


            return "Finished task";
        }


        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        @Override
        protected void onPostExecute(String s) {
            //the parameter String s will be "Finished task" from line 27
            System.out.println("1111");
            definition.setText(definitions);
            deleteButton.setOnClickListener( clk -> {
                System.out.println("delete btn");
                if(isTablet) { //both the list and details are on the screen:
                    Dict_MainActivity parent = (Dict_MainActivity)getActivity();
                    parent.deleteWord(id); //this deletes the item and updates the list

                    isSavedWord(false);
                    //now remove the fragment since you deleted it from the database:
                    // this is the object to be removed, so remove(this):
                }
                //for Phone:
                else //You are only looking at the details, you need to go back to the previous list page
                {
                    Dict_EmptyActivity parent = (Dict_EmptyActivity) getActivity();
                    Intent backToFragmentExample = new Intent();
                    backToFragmentExample.putExtra("wordID", id);

                    parent.setResult(Activity.RESULT_OK, backToFragmentExample); //send data back to FragmentExample in onActivityResult()
                    parent.finish(); //go back
                }
            });
            saveButton.setOnClickListener(click -> {
                if (isTablet) {
                    Dict_MainActivity parent = (Dict_MainActivity)getActivity();
                    id = parent.insertWord(word.getText().toString(), pronunciation.getText().toString(), definitions);
                }else {
                    Dict_EmptyActivity parent = (Dict_EmptyActivity)getActivity();
                    Intent backToFragmentExample = new Intent();
                    backToFragmentExample.putExtra("word", word.getText().toString());
                    backToFragmentExample.putExtra("pronunciation", pronunciation.getText().toString());
                    backToFragmentExample.putExtra("definitions", definitions);

                    parent.setResult(35, backToFragmentExample); //send data back to FragmentExample in onActivityResult()
                    parent.finish(); //go back
                }
                if (id > 0)
                    isSavedWord(true);




            });
            System.out.println(2222);

        }
    }


    /**
     * This method to get the inner text (text outside tags)
     * By Maarten at StackOverflow
     * https://stackoverflow.com/questions/16069425/xmlpullparser-get-inner-text-including-xml-tags/16069754#16069754
     *
     * @param parser
     * @return
     * @throws XmlPullParserException
     * @throws IOException
     */
    public String getInnerXml(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        StringBuilder sb = new StringBuilder();
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    if (depth > 0) {
                        sb.append("</" + parser.getName() + ">");
                    }
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    StringBuilder attrs = new StringBuilder();
                    for (int i = 0; i < parser.getAttributeCount(); i++) {
                        attrs.append(parser.getAttributeName(i) + "=\""
                                + parser.getAttributeValue(i) + "\" ");
                    }
                    sb.append("<" + parser.getName() + " " + attrs.toString() + ">");
                    break;
                default:
                    sb.append(parser.getText());
                    break;
            }
        }
        String content = sb.toString();
        return content;
    }


    private void isSavedWord(Boolean isSaved){
        if (isSaved){
            saveButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.VISIBLE);
        }else {
            saveButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.GONE);
        }
    }
}
