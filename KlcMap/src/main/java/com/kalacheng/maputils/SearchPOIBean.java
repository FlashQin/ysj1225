package com.kalacheng.maputils;

import com.tencent.lbssearch.httpresponse.AdInfo;
import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.object.result.SearchResultObject;
import com.tencent.map.tools.json.JsonComposer;

import java.util.List;

public class SearchPOIBean extends BaseObject {
    public int count;
    public List<SearchResultData> data;

    public SearchPOIBean() {
    }

    public static final class SearchResultData extends JsonComposer {
        public String id;
        public String title;
        public String address;
        public String tel;
        public String category;
        public String type;
        public double _distance;
        public LatLng location;
        public AdInfo ad_info;
        public SearchResultObject.SearchResultData.Pano pano;

        public SearchResultData() {
        }

        public static final class Pano extends JsonComposer {
            public String id;
            public int heading;
            public int pitch;

            public int zoom;

            public Pano() {
            }
        }
        class LatLng{
            public double lat;
            public double lng;
            public double altitude;
        }
    }
}
