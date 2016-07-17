package com.yadli.yadlisettings;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;

public class SettingsActivity extends AppCompatActivity {

    private int normal_max_big_core = 1;
    private int normal_min_big_core = 0;

    private int normal_max_little_core = 3;
    private int normal_min_little_core = 1;

    private int performance_max_big_core = 2;
    private int performance_min_big_core = 1;

    private int performance_max_little_core = 4;
    private int performance_min_little_core = 1;

    private int standby_max_big_core = 0;
    private int standby_min_big_core = 0;

    private int standby_max_little_core = 2;
    private int standby_min_little_core = 1;

    private int normal_freq_idx = 10;
    private int performance_freq_idx = 10;
    private int standby_freq_idx = 4;

    private int nightshift = 1;
    private int nightshift_start_hour = 19;
    private int nightshift_start_minute = 30;
    private int nightshift_end_hour = 20;
    private int nightshift_end_minute = 30;
    private int nightshift_restore_hour = 7;
    private int nightshift_restore_minute = 30;

    private final int[] c_freq_array = new int[]{ 384000, 460000, 600000, 672000, 768000, 864000, 960000, 1248000, 1344000, 1478000, 1555200 };
    private final int c_freq_array_size = 11;

    private final String c_config_file_name = "yadlisettings.config";


    private NumberPicker numberPicker_normal_max_big_core;
    private NumberPicker numberPicker_normal_min_big_core;
    private NumberPicker numberPicker_normal_max_little_core;
    private NumberPicker numberPicker_normal_min_little_core;
    private NumberPicker numberPicker_standby_max_big_core;
    private NumberPicker numberPicker_standby_min_big_core;
    private NumberPicker numberPicker_standby_max_little_core;
    private NumberPicker numberPicker_standby_min_little_core;
    private NumberPicker numberPicker_performance_max_big_core;
    private NumberPicker numberPicker_performance_min_big_core;
    private NumberPicker numberPicker_performance_max_little_core;
    private NumberPicker numberPicker_performance_min_little_core;
    private SeekBar seekBar_normal_freq;
    private SeekBar seekBar_performance_freq;
    private SeekBar seekBar_standby_freq;
    private TextView textView_normal_freq;
    private TextView textView_performance_freq;
    private TextView textView_standby_freq;
    private Switch switch_nightshift;
    private TimePicker timePicker_nightshift_start;
    private TimePicker timePicker_nightshift_end;
    private TimePicker timePicker_nightshift_restore;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        saveConfig();
        return true;
    }

    private String getConfigFilePath()
    {
        File root = Environment.getExternalStorageDirectory();
        return root.getAbsolutePath() + "/" + c_config_file_name;
    }


    public void getViews() {
        numberPicker_normal_max_big_core = (NumberPicker)findViewById(R.id.numberPicker_normal_max_big_core);
        numberPicker_normal_min_big_core = (NumberPicker)findViewById(R.id.numberPicker_normal_min_big_core);
        numberPicker_normal_max_little_core = (NumberPicker)findViewById(R.id.numberPicker_normal_max_little_core);
        numberPicker_normal_min_little_core = (NumberPicker)findViewById(R.id.numberPicker_normal_min_little_core);
        numberPicker_standby_max_big_core = (NumberPicker)findViewById(R.id.numberPicker_standby_max_big_core);
        numberPicker_standby_min_big_core = (NumberPicker)findViewById(R.id.numberPicker_standby_min_big_core);
        numberPicker_standby_max_little_core = (NumberPicker)findViewById(R.id.numberPicker_standby_max_little_core);
        numberPicker_standby_min_little_core = (NumberPicker)findViewById(R.id.numberPicker_standby_min_little_core);
        numberPicker_performance_max_big_core = (NumberPicker)findViewById(R.id.numberPicker_performance_max_big_core);
        numberPicker_performance_min_big_core = (NumberPicker)findViewById(R.id.numberPicker_performance_min_big_core);
        numberPicker_performance_max_little_core = (NumberPicker)findViewById(R.id.numberPicker_performance_max_little_core);
        numberPicker_performance_min_little_core = (NumberPicker)findViewById(R.id.numberPicker_performance_min_little_core);

        seekBar_normal_freq = (SeekBar)findViewById(R.id.seekBarNormalFreq);
        seekBar_performance_freq = (SeekBar)findViewById(R.id.seekBarPerformanceFreq);
        seekBar_standby_freq = (SeekBar)findViewById(R.id.seekBarStandbyFreq);

        textView_normal_freq = (TextView)findViewById(R.id.textViewNormalFreq);
        textView_performance_freq = (TextView)findViewById(R.id.textViewPerformanceFreq);
        textView_standby_freq = (TextView)findViewById(R.id.textViewStandbyFreq);

        switch_nightshift = (Switch)findViewById(R.id.switch1);
        timePicker_nightshift_start = (TimePicker)findViewById(R.id.nightshift_start_picker);
        timePicker_nightshift_end = (TimePicker)findViewById(R.id.nightshift_end_picker);
        timePicker_nightshift_restore = (TimePicker)findViewById(R.id.nightshift_restore_picker);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getViews();

        //GROUP
        numberPicker_normal_max_big_core.setMinValue(0);
        numberPicker_normal_max_big_core.setMaxValue(4);
        numberPicker_normal_max_big_core.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);


        numberPicker_normal_min_big_core.setMinValue(0);
        numberPicker_normal_min_big_core.setMaxValue(4);
        numberPicker_normal_min_big_core.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        numberPicker_normal_max_little_core.setMinValue(1);
        numberPicker_normal_max_little_core.setMaxValue(4);
        numberPicker_normal_max_little_core.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        numberPicker_normal_min_little_core.setMinValue(1);
        numberPicker_normal_min_little_core.setMaxValue(4);
        numberPicker_normal_min_little_core.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        //GROUP
        numberPicker_standby_max_big_core.setMinValue(0);
        numberPicker_standby_max_big_core.setMaxValue(4);
        numberPicker_standby_max_big_core.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        numberPicker_standby_min_big_core.setMinValue(0);
        numberPicker_standby_min_big_core.setMaxValue(4);
        numberPicker_standby_min_big_core.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        numberPicker_standby_max_little_core.setMinValue(1);
        numberPicker_standby_max_little_core.setMaxValue(4);
        numberPicker_standby_max_little_core.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        numberPicker_standby_min_little_core.setMinValue(1);
        numberPicker_standby_min_little_core.setMaxValue(4);
        numberPicker_standby_min_little_core.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        //GROUP
        numberPicker_performance_max_big_core.setMinValue(0);
        numberPicker_performance_max_big_core.setMaxValue(4);
        numberPicker_performance_max_big_core.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        numberPicker_performance_min_big_core.setMinValue(0);
        numberPicker_performance_min_big_core.setMaxValue(4);
        numberPicker_performance_min_big_core.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        numberPicker_performance_max_little_core.setMinValue(1);
        numberPicker_performance_max_little_core.setMaxValue(4);
        numberPicker_performance_max_little_core.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        numberPicker_performance_min_little_core.setMinValue(1);
        numberPicker_performance_min_little_core.setMaxValue(4);
        numberPicker_performance_min_little_core.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        seekBar_normal_freq.setMax(c_freq_array_size - 1);
        seekBar_performance_freq.setMax(c_freq_array_size - 1);
        seekBar_standby_freq.setMax(c_freq_array_size - 1);


        seekBar_normal_freq.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setFreqDisplay(textView_normal_freq, progress);
                normal_freq_idx = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBar_performance_freq.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setFreqDisplay(textView_performance_freq, progress);
                performance_freq_idx = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBar_standby_freq.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setFreqDisplay(textView_standby_freq, progress);
                standby_freq_idx = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        loadConfig();

    }

    private void setFreqDisplay(TextView textView, int progress) {
        textView.setText(c_freq_array[progress] / 1000 + "Mhz");
    }

    private void loadConfig() {
        try {
            FileInputStream stream = new FileInputStream(getConfigFilePath());
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader br = new BufferedReader( reader ) ;

            String input;

            while(null != (input = br.readLine()))
            {
                String[] input_segments = input.split("=");
                int value = Integer.valueOf(input_segments[1]);
                switch(input_segments[0])
                {
                    case "normal_max_big_core": { normal_max_big_core = value; break; }
                    case "normal_min_big_core": { normal_min_big_core = value; break; }

                    case "normal_max_little_core": { normal_max_little_core = value; break; }
                    case "normal_min_little_core": { normal_min_little_core = value; break; }

                    case "performance_max_big_core": { performance_max_big_core = value; break; }
                    case "performance_min_big_core": { performance_min_big_core = value; break; }

                    case "performance_max_little_core": { performance_max_little_core = value; break; }
                    case "performance_min_little_core": { performance_min_little_core = value; break; }

                    case "standby_max_big_core": { standby_max_big_core = value; break; }
                    case "standby_min_big_core": { standby_min_big_core = value; break; }

                    case "standby_max_little_core": { standby_max_little_core = value; break; }
                    case "standby_min_little_core": { standby_min_little_core = value; break; }

                    case "normal_freq": { normal_freq_idx = findFreqIdx(value); break; }
                    case "standby_freq": {standby_freq_idx = findFreqIdx(value); break;}
                    case "performance_freq": {performance_freq_idx = findFreqIdx(value); break;}

                    case "nightshift_start_hour": {nightshift_start_hour = value; break; }
                    case "nightshift_start_minute": {nightshift_start_minute = value; break; }
                    case "nightshift_end_hour": {nightshift_end_hour = value; break; }
                    case "nightshift_end_minute": {nightshift_end_minute = value; break; }
                    case "nightshift_restore_hour": {nightshift_restore_hour = value; break; }
                    case "nightshift_restore_minute": {nightshift_restore_minute = value; break; }

                    case "nightshift" : {nightshift = value; break; }
                    
                }
            }

            br.close();
            reader.close();
            stream.close();

        } catch (Exception e) {
        }

        // set values

        setFreqDisplay(textView_normal_freq, normal_freq_idx);
        setFreqDisplay(textView_performance_freq, performance_freq_idx);
        setFreqDisplay(textView_standby_freq, standby_freq_idx);

        seekBar_normal_freq.setProgress(normal_freq_idx);
        seekBar_performance_freq.setProgress(performance_freq_idx);
        seekBar_standby_freq.setProgress(standby_freq_idx);

        numberPicker_normal_max_big_core.setValue(normal_max_big_core);
        numberPicker_normal_min_big_core.setValue(normal_min_big_core);

        numberPicker_normal_max_little_core.setValue(normal_max_little_core);
        numberPicker_normal_min_little_core.setValue(normal_min_little_core);

        numberPicker_performance_max_big_core.setValue(performance_max_big_core);
        numberPicker_performance_min_big_core.setValue(performance_min_big_core);

        numberPicker_performance_max_little_core.setValue(performance_max_little_core);
        numberPicker_performance_min_little_core.setValue(performance_min_little_core);

        numberPicker_standby_max_big_core.setValue(standby_max_big_core);
        numberPicker_standby_min_big_core.setValue(standby_min_big_core);

        numberPicker_standby_max_little_core.setValue(standby_max_little_core);
        numberPicker_standby_min_little_core.setValue(standby_min_little_core);

        switch_nightshift.setChecked(nightshift != 0);
        timePicker_nightshift_start.setCurrentHour(nightshift_start_hour);
        timePicker_nightshift_start.setCurrentMinute(nightshift_start_minute);
        timePicker_nightshift_end.setCurrentHour(nightshift_end_hour);
        timePicker_nightshift_end.setCurrentMinute(nightshift_end_minute);
        timePicker_nightshift_restore.setCurrentHour(nightshift_restore_hour);
        timePicker_nightshift_restore.setCurrentMinute(nightshift_restore_minute);
    }

    private int findFreqIdx(int value) {
        for(int i=0;i<c_freq_array_size;++i) {
            if(c_freq_array[i] == value)return i;
        }
        return 0;
    }

    private void saveConfig() {

        normal_max_big_core = numberPicker_normal_max_big_core.getValue();
        normal_min_big_core = numberPicker_normal_min_big_core.getValue();
        normal_max_little_core = numberPicker_normal_max_little_core.getValue();
        normal_min_little_core = numberPicker_normal_min_little_core.getValue();
        //GROUP
        standby_max_big_core = numberPicker_standby_max_big_core.getValue();
        standby_min_big_core = numberPicker_standby_min_big_core.getValue();
        standby_max_little_core = numberPicker_standby_max_little_core.getValue();
        standby_min_little_core = numberPicker_standby_min_little_core.getValue();
        //GROUP
        performance_max_big_core = numberPicker_performance_max_big_core.getValue();
        performance_min_big_core = numberPicker_performance_min_big_core.getValue();
        performance_max_little_core = numberPicker_performance_max_little_core.getValue();
        performance_min_little_core = numberPicker_performance_min_little_core.getValue();

        nightshift = switch_nightshift.isChecked() ? 1 : 0;
        nightshift_start_hour = timePicker_nightshift_start.getCurrentHour();
        nightshift_start_minute = timePicker_nightshift_start.getCurrentMinute();
        nightshift_end_hour = timePicker_nightshift_end.getCurrentHour();
        nightshift_end_minute = timePicker_nightshift_end.getCurrentMinute();
        nightshift_restore_hour = timePicker_nightshift_restore.getCurrentHour();
        nightshift_restore_minute = timePicker_nightshift_restore.getCurrentMinute();

        try {
            FileOutputStream stream = new FileOutputStream(getConfigFilePath());
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            BufferedWriter bw = new BufferedWriter(writer);

            bw.write("normal_max_big_core=");
            bw.write(Integer.toString(normal_max_big_core));
            bw.newLine();
            bw.write("normal_min_big_core=");
            bw.write(Integer.toString(normal_min_big_core));
            bw.newLine();

            bw.write("normal_max_little_core=");
            bw.write(Integer.toString(normal_max_little_core));
            bw.newLine();
            bw.write("normal_min_little_core=");
            bw.write(Integer.toString(normal_min_little_core));
            bw.newLine();

            bw.write("performance_max_big_core=");
            bw.write(Integer.toString(performance_max_big_core));
            bw.newLine();
            bw.write("performance_min_big_core=");
            bw.write(Integer.toString(performance_min_big_core));
            bw.newLine();

            bw.write("performance_max_little_core=");
            bw.write(Integer.toString(performance_max_little_core));
            bw.newLine();
            bw.write("performance_min_little_core=");
            bw.write(Integer.toString(performance_min_little_core));
            bw.newLine();

            bw.write("standby_max_big_core=");
            bw.write(Integer.toString(standby_max_big_core));
            bw.newLine();
            bw.write("standby_min_big_core=");
            bw.write(Integer.toString(standby_min_big_core));
            bw.newLine();

            bw.write("standby_max_little_core=");
            bw.write(Integer.toString(standby_max_little_core));
            bw.newLine();
            bw.write("standby_min_little_core=");
            bw.write(Integer.toString(standby_min_little_core));
            bw.newLine();

            bw.write("normal_freq=");
            bw.write(Integer.toString(c_freq_array[normal_freq_idx]));
            bw.newLine();
            bw.write("performance_freq=");
            bw.write(Integer.toString(c_freq_array[performance_freq_idx]));
            bw.newLine();
            bw.write("standby_freq=");
            bw.write(Integer.toString(c_freq_array[standby_freq_idx]));
            bw.newLine();

            bw.write("nightshift=");
            bw.write(Integer.toString(nightshift));
            bw.newLine();
            bw.write("nightshift_start_hour=");
            bw.write(Integer.toString(nightshift_start_hour));
            bw.newLine();
            bw.write("nightshift_start_minute=");
            bw.write(Integer.toString(nightshift_start_minute));
            bw.newLine();
            bw.write("nightshift_end_hour=");
            bw.write(Integer.toString(nightshift_end_hour));
            bw.newLine();
            bw.write("nightshift_end_minute=");
            bw.write(Integer.toString(nightshift_end_minute));
            bw.newLine();
            bw.write("nightshift_restore_hour=");
            bw.write(Integer.toString(nightshift_restore_hour));
            bw.newLine();
            bw.write("nightshift_restore_minute=");
            bw.write(Integer.toString(nightshift_restore_minute));
            bw.newLine();


            bw.close();
            writer.close();
            stream.close();
        }catch(Exception e){
            Log.d("Yadli", e.toString());
        }

        // Fire a message
        Toast.makeText(this, getString(R.string.save_message), Toast.LENGTH_LONG).show();
    }


/* FYI

            normal_max_big_core
            normal_min_big_core

            normal_max_little_core
            normal_min_little_core

            performance_max_big_core
            performance_min_big_core

            performance_max_little_core
            performance_min_little_core

            standby_max_big_core
            standby_min_big_core

            standby_max_little_core
            standby_min_little_core
*/

}
