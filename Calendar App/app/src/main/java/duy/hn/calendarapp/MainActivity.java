package duy.hn.calendarapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Khai báo biến toàn cục
    private int ngayHienTai=0,
                thangHienTai=0,
                namHienTai=0;
    private int index=0;

    private List<String> calendarStrings;
    private int[] Ngays, Thangs, Nams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CalendarView calendarView = findViewById(R.id.calendarView);

        //Tạo nơi lưu trữ dòng ghi chú
        final List<String> calendarStrings=new ArrayList<>();

        final int soNgay=2000;

        Ngays=new int[soNgay];
        Thangs=new int[soNgay];
        Nams=new int[soNgay];

        docThongTin();

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

        //Tạo nút và bộ lắng nghe xử lý chức năng trở về ngày hôm nay
        final Button todayBtn=findViewById(R.id.todayBtn);
        todayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.setDate(calendarView.getDate());
            }
        });
    }
    //Xử lý khi tắt ứng dụng hoàn toàn
    @Override
    protected void onPause(){
        super.onPause();
        luuThongTin();
    }
    //Lưu thông tin ghi chú của người dùng vào một tập tin
    private void luuThongTin(){
        try {
            FileWriter fileWriter=new FileWriter("calendarStrings");
            final int calendarStringsCount = calendarStrings.size();
            for (int i=0; i<calendarStringsCount;i++){
                fileWriter.write(calendarStrings.get(i));
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Đọc thông tin từ tập tin
    private void docThongTin(){
        try {
            BufferedReader docTT = new BufferedReader(new FileReader("calendarStrings"));
            String line= null;
            while ((line=docTT.readLine())!= null){
                calendarStrings.add(line);
            }
            docTT.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
