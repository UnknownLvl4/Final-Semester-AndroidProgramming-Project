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
    private int currentDay=0, currentMonth=0, currentYear=0, daysIndex=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CalendarView calendarView = findViewById(R.id.calendarView);

        //Tạo nơi lưu trữ dòng ghi chú
        final List<String> calendarStrings=new ArrayList<>();
        int[] days=new int[30];
        final EditText textInput = findViewById(R.id.textInput);

        final View noiDung=findViewById(R.id.noiDung);

        //Cài đặt bộ lắng nghe khi user nhấn vào một ngày trên lịch
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //Cập nhật biến toàn cục
                currentDay=dayOfMonth;
                currentMonth=month;
                currentYear=year;
                if (noiDung.getVisibility()==View.GONE){
                    noiDung.setVisibility(View.VISIBLE);
                }


                for (int i=0; i<30; i++){
                    if (days[i]==currentDay){
                        textInput.setText(calendarStrings.get(i));
                        return;
                    }
                }
                textInput.setText("");
            }
        });
        final Button saveBtn = findViewById(R.id.saveBtn);
        //Cài đặt bộ lắng nghe lưu lại dữ liệu sau khi nhấn nút "Lưu"
        saveBtn.setOnClickListener(v -> {                           //Rút gọn sự kiện bằng biểu thức lambda
            days[daysIndex]=currentDay;
            calendarStrings.add(daysIndex,textInput.getText().toString());
            daysIndex++;
            textInput.setText("");
        });
    }
}