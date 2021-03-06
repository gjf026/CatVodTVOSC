package com.github.tvbox.osc.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.github.tvbox.osc.cache.Cache;
import com.github.tvbox.osc.cache.CacheDao;
import com.github.tvbox.osc.cache.LocalLive;
import com.github.tvbox.osc.cache.LocalLiveDao;
import com.github.tvbox.osc.cache.LocalParse;
import com.github.tvbox.osc.cache.LocalParseDao;
import com.github.tvbox.osc.cache.LocalSource;
import com.github.tvbox.osc.cache.LocalSourceDao;
import com.github.tvbox.osc.cache.SourceState;
import com.github.tvbox.osc.cache.SourceStateDao;
import com.github.tvbox.osc.cache.VodRecord;
import com.github.tvbox.osc.cache.VodRecordDao;


/**
 * 类描述:
 *
 * @author pj567
 * @since 2020/5/15
 */
@Database(entities = {Cache.class, VodRecord.class, LocalSource.class, SourceState.class, LocalParse.class, LocalLive.class}, version = 3)
public abstract class AppDataBase extends RoomDatabase {
    public abstract CacheDao getCacheDao();

    public abstract VodRecordDao getVodRecordDao();

    public abstract LocalSourceDao getLocalSourceDao();

    public abstract SourceStateDao getSourceStateDao();

    public abstract LocalParseDao getLocalParseDao();

    public abstract LocalLiveDao getLocalLiveDao();
}
