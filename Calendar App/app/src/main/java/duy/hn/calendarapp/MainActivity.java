package duy.hn.calendarapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Khai báo biến toàn cục
    private int ngayHienTai = 0,
            thangHienTai = 0,
            namHienTai = 0;
    private int index = 0;
    private List<String> calendarStrings;
    private int[] Ngay, Thang, Nam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CalendarView calendarView = findViewById(R.id.calendarView);

        //Tạo nơi lưu trữ dòng ghi chú
        calendarStrings = new ArrayList<>();

        final int soNgay = 2000;

        Ngay = new int[soNgay];
        Thang = new int[soNgay];
        Nam = new int[soNgay];

        docThongTin();

        final EditText noiDungNhap = findViewById(R.id.textInput);

        final View noiDung = findViewById(R.id.noiDung);

        //Cài đặt bộ lắng nghe khi user nhấn vào một ngày trên lịch
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //Cập nhật biến toàn cục
                ngayHienTai = dayOfMonth;
                thangHienTai = month;
                namHienTai = year;
                if (noiDung.getVisibility() == View.GONE) {
                    noiDung.setVisibility(View.VISIBLE);
                }

                int noteIndex = timViTriGhiChu(dayOfMonth, month, year);
                if (noteIndex != -1) {
                    noiDungNhap.setText(calendarStrings.get(noteIndex));
                } else {
                    noiDungNhap.setText("");
                }
            }
        });

        final Button saveBtn = findViewById(R.id.saveBtn);
        //Cài đặt bộ lắng nghe lưu lại dữ liệu sau khi nhấn nút "Lưu"
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int existingNoteIndex = timViTriGhiChu(ngayHienTai, thangHienTai, namHienTai);

                if (existingNoteIndex != -1) {
                    //Cập nhật ghi chú cũ
                    calendarStrings.set(existingNoteIndex, noiDungNhap.getText().toString());
                } else {
                    // Thêm ghi chú mới
                    Ngay[index] = ngayHienTai;
                    Thang[index] = thangHienTai;
                    Nam[index] = namHienTai;
                    calendarStrings.add(noiDungNhap.getText().toString());
                    index++;
                }
                noiDungNhap.setText("");
                noiDung.setVisibility(View.GONE);
            }
        });

        //Tạo nút và bộ lắng nghe xử lý chức năng trở về ngày hôm nay
        final Button todayBtn = findViewById(R.id.todayBtn);
        todayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.setDate(calendarView.getDate());
            }
        });
    }

    //Xử lý khi ngắt hoạt động ứng dụng
    @Override
    protected void onPause() {
        super.onPause();
        luuThongTin();
    }

    //Lưu thông tin ghi chú của người dùng vào một tập tin
    private void luuThongTin() {
        File file = new File(this.getFilesDir(), "saved");
        File thongTinNgay = new File(this.getFilesDir(), "Ngay");
        File thongTinThang = new File(this.getFilesDir(), "Month");
        File thongTinNam = new File(this.getFilesDir(), "Nam");

        try (FileOutputStream fOut = new FileOutputStream(file);                    //Dùng cấu trúc try-with-resources
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fOut));

             FileOutputStream fOutNgay = new FileOutputStream(thongTinNgay);
             BufferedWriter bwNgay = new BufferedWriter(new OutputStreamWriter(fOutNgay));

             FileOutputStream fOutThang = new FileOutputStream(thongTinThang);
             BufferedWriter bwThang = new BufferedWriter(new OutputStreamWriter(fOutThang));

             FileOutputStream fOutNam = new FileOutputStream(thongTinNam);
             BufferedWriter bwNam = new BufferedWriter(new OutputStreamWriter(fOutNam))) {


            for (int i = 0; i < index; i++) {
                bw.write(calendarStrings.get(i));
                bw.newLine();
                bwNgay.write(Ngay[i]);
                bwThang.write(Thang[i]);
                bwNam.write(Nam[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Đọc thông tin từ tập tin
    private void docThongTin() {
        File file = new File(this.getFilesDir(), "saved");
        File thongTinNgay = new File(this.getFilesDir(), "Ngay");
        File thongTinThang = new File(this.getFilesDir(), "Month");
        File thongTinNam = new File(this.getFilesDir(), "Nam");

        if (!file.exists()) {
            return;
        }

        try (FileInputStream is = new FileInputStream(file);
             BufferedReader docTT = new BufferedReader(new InputStreamReader(is));
             FileInputStream isNgay = new FileInputStream(thongTinNgay);
             BufferedReader docTTNgay = new BufferedReader(new InputStreamReader(isNgay));
             FileInputStream isThang = new FileInputStream(thongTinThang);
             BufferedReader docTTThang = new BufferedReader(new InputStreamReader(isThang));
             FileInputStream isNam = new FileInputStream(thongTinNam);
             BufferedReader docTTNam = new BufferedReader(new InputStreamReader(isNam))) {

            int i = 0;
            String dong;                                    //Đọc từng dòng một trong tập tin
            while ((dong = docTT.readLine()) != null) {
                calendarStrings.add(dong);
                Ngay[i] = docTTNgay.read();
                Thang[i] = docTTThang.read();
                Nam[i] = docTTNam.read();
                i++;
            }
            index = i;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Tìm vị trí ghi chú theo ngày, tháng, năm
    private int timViTriGhiChu(int day, int month, int year) {
        for (int i = 0; i < index; i++) {
            if (Ngay[i] == day && Thang[i] == month && Nam[i] == year) {
                return i;
            }
        }
        return -1; // Không tìm thấy
    }
}