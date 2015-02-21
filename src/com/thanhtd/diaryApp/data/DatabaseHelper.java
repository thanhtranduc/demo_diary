package com.thanhtd.diaryApp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.thanhtd.diaryApp.data.model.ItemModel;

import java.sql.SQLException;

/**
 * Created by a on 21/02/2015.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper
{
    private String databaseName;
    private Context context;
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context, String databaseName)
    {
        super(context, databaseName, null, DATABASE_VERSION);
        this.databaseName = databaseName;
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource)
    {
        try
        {
            TableUtils.createTable(connectionSource, ItemModel.class);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2)
    {

    }

    public Dao<ItemModel, Long> getDaoItem() throws SQLException
    {
        return DaoManager.createDao(getConnectionSource(), ItemModel.class);
    }
}
