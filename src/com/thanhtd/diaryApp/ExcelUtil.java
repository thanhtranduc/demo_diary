package com.thanhtd.diaryApp;

import android.os.Environment;
import com.thanhtd.diaryApp.data.model.ItemModel;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 4/16/2015.
 */
public class ExcelUtil
{
    public static void exportToExcel(List<ItemModel> itemModels)
    {
        final String fileName = "reportDiaryApp.xls";

        //Saving file in external storage
        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File(sdCard.getAbsolutePath() + "/BrianSmithReport");

        //create directory if not exist
        if (!directory.isDirectory())
        {
            directory.mkdirs();
        }

        //file path
        File file = new File(directory, fileName);

        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook;

        try
        {
            workbook = Workbook.createWorkbook(file, wbSettings);
            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet("ReportDiaryApp", 0);
            try
            {

                sheet.addCell(new Label(0, 0, "Report created using Brian Smith App"));
                sheet.addCell(new Label(0, 2, "Systolic")); // column and row
                sheet.addCell(new Label(1, 2, "Diastolic"));
                sheet.addCell(new Label(2, 2, "Pulse"));
                sheet.addCell(new Label(3, 2, "Comment"));
                sheet.addCell(new Label(4, 2, "Place of Measurement"));
                sheet.addCell(new Label(5, 2, "Measurement Position"));
                sheet.addCell(new Label(6, 2, "Time"));
                sheet.addCell(new Label(7, 2, "Date"));
                int i = 3;
                for (ItemModel itemModel : itemModels)
                {
                    String systol = itemModel.getSystol();
                    String diasol = itemModel.getDiasol();
                    String pulse = itemModel.getPulse();
                    String comment = itemModel.getComment();
                    String placeOfMeasurement = getPlaceOfMeasurement(itemModel.getPlaceMeasurement().intValue());
                    String measurementPosition = getMeasurementPosition(itemModel.getPositionMeasurement().intValue());
                    String time = "";
                    if (itemModel.getTime() != null)
                    {
                        DateFormat formatter = new SimpleDateFormat("hh:mm a");
                        time = formatter.format(itemModel.getTime());
                    }
                    String date = "";
                    if (itemModel.getDate() != null)
                    {
                        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
                        date = df2.format(itemModel.getDate());
                    }
                    sheet.addCell(new Label(0, i, systol));
                    sheet.addCell(new Label(1, i, diasol));
                    sheet.addCell(new Label(2, i, pulse));
                    sheet.addCell(new Label(3, i, comment));
                    sheet.addCell(new Label(4, i, placeOfMeasurement));
                    sheet.addCell(new Label(5, i, measurementPosition));
                    sheet.addCell(new Label(6, i, time));
                    sheet.addCell(new Label(7, i, date));
                    i++;
                }
                System.out.println("ok");
            }
            catch (RowsExceededException e)
            {
                e.printStackTrace();
            }
            catch (WriteException e)
            {
                e.printStackTrace();
            }
            workbook.write();
            try
            {
                workbook.close();
            }
            catch (WriteException e)
            {
                e.printStackTrace();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static String getPlaceOfMeasurement(int index)
    {
        switch (index)
        {
            case 0:
                return "Right Arm";
            case 1:
                return "Left Arm";
            case 2:
                return "Right Wrist";
            case 3:
                return "Left Wrist";
            case 4:
                return "Right Leg";
            case 5:
                return "Left Leg";
        }
        return "Empty";
    }

    public static String getMeasurementPosition(int index)
    {
        switch (index)
        {
            case 0:
                return "Sitting";
            case 1:
                return "Standing";
            case 2:
                return "Horizontal";
        }
        return "Empty";
    }
}
