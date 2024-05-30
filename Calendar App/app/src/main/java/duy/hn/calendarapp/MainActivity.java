package duy.hn.calendarapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Khai báo biến toàn cục
    private int currentDay=0;
    private int currentMonth=0;
    private int currentYear=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CalendarView calendarView = findViewById(R.id.calendarView);
        final TextView selectedDay = findViewById(R.id.selectedDay);
        final TextView selectedMonth = findViewById(R.id.selectedMonth);
        final TextView selectedYear = findViewById(R.id.selectedYear);

        final List<String> calendarStrings=new ArrayList<>();
        int[] days=new int[30];
        final EditText textInput = findViewById(R.id.textInput);

        final View noiDung=findViewById(R.id.noiDung);

        //Cài đặt bộ lắng nghe khi user nhấn vào một ngày trên lịch
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDay.setText("Selected Day:" + dayOfMonth);
                selectedMonth.setText("Selected Month:" + month);
                selectedYear.setText("Selected Year:" + year);
                //Cập nhật biến toàn cục
                currentDay=dayOfMonth;
                currentMonth=month;
                currentYear=year;
                if (noiDung.getVisibility()==View.GONE){
                    noiDung.setVisibility(View.VISIBLE);
                }
                if (currentDay==days[0]){
                    textInput.setText(calendarStrings.get(0));
                }
            }
        });
        final Button saveBtn = findViewById(R.id.saveBtn);
        //Cai dat bo lang nghe luu lai data sau khi nhan nut "Luu"
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days[0]=currentDay;
                calendarStrings.add(textInput.getText().toString());
                textInput.setText("");
            }
        });
    }
}