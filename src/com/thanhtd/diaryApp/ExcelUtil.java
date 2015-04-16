package com.thanhtd.diaryApp;

import android.database.Cursor;
import android.os.Environment;
import com.thanhtd.diaryApp.data.DatabaseHelper;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by Administrator on 4/16/2015.
 */
public class ExcelUtil
{
    private void exportToExcel(Cursor cursor)
    {
        final String fileName = "TodoList.xls";

        //Saving file in external storage
        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File(sdCard.getAbsolutePath() + "/javatechig.todo");

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
            WritableSheet sheet = workbook.createSheet("MyShoppingList", 0);

            try
            {
                sheet.addCell(new Label(0, 0, "Subject")); // column and row
                sheet.addCell(new Label(1, 0, "Description"));
                if (cursor.moveToFirst())
                {
                    do
                    {
                        String title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TODO_SUBJECT));
                        String desc = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TODO_DESC));

                        int i = cursor.getPosition() + 1;
                        sheet.addCell(new Label(0, i, title));
                        sheet.addCell(new Label(1, i, desc));
                    } while (cursor.moveToNext());
                }
                //closing cursor
                cursor.close();
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
}
