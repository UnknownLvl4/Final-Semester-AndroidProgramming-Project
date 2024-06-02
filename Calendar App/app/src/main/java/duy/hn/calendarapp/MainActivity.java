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
    private int ngayHienTai=0,
                thangHienTai=0,
                namHienTai=0;
    private int index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CalendarView calendarView = findViewById(R.id.calendarView);

        //Tạo nơi lưu trữ dòng ghi chú
        final List<String> calendarStrings=new ArrayList<>();
        final int soNgay=2000;
        final int[] Ngays=new int[soNgay],
                    Thangs=new int[soNgay],
                    Nams=new int[soNgay];

        final EditText textInput = findViewById(R.id.textInput);

        final View noiDung=findViewById(R.id.noiDung);

        //Cài đặt bộ lắng nghe khi user nhấn vào một ngày trên lịch
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //Cập nhật biến toàn cục
                ngayHienTai=dayOfMonth;
                thangHienTai=month;
                namHienTai=year;
                if (noiDung.getVisibility()==View.GONE){
                    noiDung.setVisibility(View.VISIBLE);
                }

                for (int k=0;k<index;k++){
                    if (Nams[k]==namHienTai){
                        for (int i=0; i<index; i++){
                            if (Ngays[i]==ngayHienTai){
                                for (int j=0;j<index;j++){
                                    if (Thangs[j]==thangHienTai && Ngays[j]==ngayHienTai && Nams[j]==namHienTai){
                                        textInput.setText(calendarStrings.get(j));
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
                textInput.setText("");
            }
        });
        final Button saveBtn = findViewById(R.id.saveBtn);
        //Cài đặt bộ lắng nghe lưu lại dữ liệu sau khi nhấn nút "Lưu"
        saveBtn.setOnClickListener(v -> {                           //Rút gọn sự kiện bằng biểu thức lambda
            Ngays[index]=ngayHienTai;
            Thangs[index]=thangHienTai;
            Nams[index]=namHienTai;
            calendarStrings.add(index,textInput.getText().toString());
            textInput.setText("");
            index++;
            noiDung.setVisibility(View.GONE);
        });
    }

}