package com.kalacheng.base.socket;

import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.base.http.HttpClient;
import com.wengying666.imsocket.IGetSocketConfig;

public class SocketConfig implements IGetSocketConfig {
    public String getToken() {
        return HttpClient.getToken();
    }

    @Override
    public String getIp() {
        return (String) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_SOCKET_IP, "");
    }

    @Override
    public int getPort() {
        return (int) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_SOCKET_PORT, 0);
    }
/*

    public URI getUrl() {
        return URI.create((String) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_SOCKET_URL, ""));
    }
*/

    public long getUID() {
        return HttpClient.getUid();
    }

    public String getImKey() {
        String strKey = (String) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_IM_KEY, "");
        return strKey;
    }
}
