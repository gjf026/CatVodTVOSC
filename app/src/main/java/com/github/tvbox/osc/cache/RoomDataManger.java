package com.github.tvbox.osc.cache;

import android.text.TextUtils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.tvbox.osc.api.ApiConfig;
import com.github.tvbox.osc.bean.SourceBean;
import com.github.tvbox.osc.bean.VodInfo;
import com.github.tvbox.osc.data.AppDataManager;

/**
 * @author pj567
 * @date :2021/1/7
 * @description:
 */
public class RoomDataManger {
    static ExclusionStrategy vodInfoStrategy = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes field) {
            if (field.getDeclaringClass() == VodInfo.class && field.getName().equals("seriesFlags")) {
                return true;
            }
            if (field.getDeclaringClass() == VodInfo.class && field.getName().equals("seriesMap")) {
                return true;
            }
            return false;
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    };

    private static Gson getVodInfoGson() {
        return new GsonBuilder().addSerializationExclusionStrategy(vodInfoStrategy).create();
    }

    public static void insertVodRecord(String sourceKey, VodInfo vodInfo) {
        VodRecord record = AppDataManager.get().getVodRecordDao().getVodRecord(sourceKey, vodInfo.id);
        if (record == null) {
            record = new VodRecord();
        }
        record.sourceKey = sourceKey;
        record.vodId = vodInfo.id;
        record.updateTime = System.currentTimeMillis();
        record.data = null;
        record.dataJson = getVodInfoGson().toJson(vodInfo);
        AppDataManager.get().getVodRecordDao().insert(record);
    }

    public static VodInfo getVodInfo(String sourceKey, String vodId) {
        VodRecord record = AppDataManager.get().getVodRecordDao().getVodRecord(sourceKey, vodId);
        try {
            if (record != null && record.dataJson != null && !TextUtils.isEmpty(record.dataJson)) {
                VodInfo vodInfo = getVodInfoGson().fromJson(record.dataJson, new TypeToken<VodInfo>() {
                }.getType());
                if (vodInfo.name == null || vodInfo.type == null)
                    return null;
                return vodInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteVodRecord(String sourceKey, VodInfo vodInfo) {
        VodRecord record = AppDataManager.get().getVodRecordDao().getVodRecord(sourceKey, vodInfo.id);
        if (record != null) {
            AppDataManager.get().getVodRecordDao().delete(record);
        }
    }

    public static List<VodInfo> getAllVodRecord() {
        List<VodRecord> recordList = AppDataManager.get().getVodRecordDao().getAll();
        List<VodInfo> vodInfoList = new ArrayList<>();
        if (recordList != null) {
            for (VodRecord record : recordList) {
                VodInfo info = null;
                try {
                    if (record.dataJson != null && !TextUtils.isEmpty(record.dataJson)) {
                        info = getVodInfoGson().fromJson(record.dataJson, new TypeToken<VodInfo>() {
                        }.getType());
                        info.sourceKey = record.sourceKey;
                        SourceBean sourceBean = ApiConfig.get().getSource(info.sourceKey);
                        if (sourceBean == null || !sourceBean.isActive() || info.name == null || info.type == null)
                            info = null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (info != null)
                    vodInfoList.add(info);
            }
        }
        return vodInfoList;
    }

    public static List<LocalSource> getAllLocalSource() {
        return AppDataManager.get().getLocalSourceDao().getAll();
    }

    public static void addLocalSource(LocalSource source) {
        AppDataManager.get().getLocalSourceDao().insert(source);
    }

    public static void delLocalSource(LocalSource source) {
        AppDataManager.get().getLocalSourceDao().delete(source);
    }

    public static HashMap<String, SourceState> getAllSourceState() {
        HashMap<String, SourceState> result = new HashMap<>();
        for (SourceState state : AppDataManager.get().getSourceStateDao().getAll()) {
            result.put(state.sourceKey, state);
        }
        return result;
    }

    public static void addSourceState(SourceState ss) {
        AppDataManager.get().getSourceStateDao().insert(ss);
    }

    public static void delSourceState(SourceState ss) {
        AppDataManager.get().getSourceStateDao().delete(ss);
    }


    public static List<LocalParse> getAllLocalParse() {
        return AppDataManager.get().getLocalParseDao().getAll();
    }

    public static void addLocalParse(LocalParse parse) {
        AppDataManager.get().getLocalParseDao().insert(parse);
    }

    public static void delLocalParse(LocalParse parse) {
        AppDataManager.get().getLocalParseDao().delete(parse);
    }

    public static List<LocalLive> getAllLocalLive() {
        return AppDataManager.get().getLocalLiveDao().getAll();
    }

    public static void addLocalLive(LocalLive ss) {
        AppDataManager.get().getLocalLiveDao().insert(ss);
    }

    public static void delLocalLive(LocalLive ss) {
        AppDataManager.get().getLocalLiveDao().delete(ss);
    }

}