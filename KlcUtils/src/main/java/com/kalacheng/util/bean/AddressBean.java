package com.kalacheng.util.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class AddressBean implements Parcelable,Cloneable {

    /**
     * areaId : 210000
     * areaName : 辽宁省
     * checked : false
     * cities : [{"areaId":"211400","areaName":"葫芦岛市","checked":false,"counties":[{"areaId":"211402","areaName":"连山区","checked":false}]}]
     */

    private String areaId;
    private String areaName;
    private boolean checked;
    private List<CitiesBean> cities;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<CitiesBean> getCities() {
        return cities;
    }

    public void setCities(List<CitiesBean> cities) {
        this.cities = cities;
    }

    public static class CitiesBean implements Parcelable,Cloneable {
        /**
         * areaId : 211400
         * areaName : 葫芦岛市
         * checked : false
         * counties : [{"areaId":"211402","areaName":"连山区","checked":false}]
         */

        private String areaId;
        private String areaName;
        private boolean checked;
        private List<CountiesBean> counties;

        public String getAreaId() {
            return areaId;
        }

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public List<CountiesBean> getCounties() {
            return counties;
        }

        public void setCounties(List<CountiesBean> counties) {
            this.counties = counties;
        }

        public static class CountiesBean implements Parcelable,Cloneable {
            /**
             * areaId : 211402
             * areaName : 连山区
             * checked : false
             */

            private String areaId;
            private String areaName;
            private boolean checked;

            public String getAreaId() {
                return areaId;
            }

            public void setAreaId(String areaId) {
                this.areaId = areaId;
            }

            public String getAreaName() {
                return areaName;
            }

            public void setAreaName(String areaName) {
                this.areaName = areaName;
            }

            public boolean isChecked() {
                return checked;
            }

            public void setChecked(boolean checked) {
                this.checked = checked;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.areaId);
                dest.writeString(this.areaName);
                dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
            }

            public CountiesBean() {
            }

            protected CountiesBean(Parcel in) {
                this.areaId = in.readString();
                this.areaName = in.readString();
                this.checked = in.readByte() != 0;
            }

            public static final Parcelable.Creator<CountiesBean> CREATOR = new Parcelable.Creator<CountiesBean>() {
                @Override
                public CountiesBean createFromParcel(Parcel source) {
                    return new CountiesBean(source);
                }

                @Override
                public CountiesBean[] newArray(int size) {
                    return new CountiesBean[size];
                }
            };

            @NonNull
            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.areaId);
            dest.writeString(this.areaName);
            dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
            dest.writeTypedList(this.counties);
        }

        public CitiesBean() {
        }

        protected CitiesBean(Parcel in) {
            this.areaId = in.readString();
            this.areaName = in.readString();
            this.checked = in.readByte() != 0;
            this.counties = in.createTypedArrayList(CountiesBean.CREATOR);
        }

        public static final Parcelable.Creator<CitiesBean> CREATOR = new Parcelable.Creator<CitiesBean>() {
            @Override
            public CitiesBean createFromParcel(Parcel source) {
                return new CitiesBean(source);
            }

            @Override
            public CitiesBean[] newArray(int size) {
                return new CitiesBean[size];
            }
        };

        @NonNull
        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.areaId);
        dest.writeString(this.areaName);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.cities);
    }

    public AddressBean() {
    }

    protected AddressBean(Parcel in) {
        this.areaId = in.readString();
        this.areaName = in.readString();
        this.checked = in.readByte() != 0;
        this.cities = in.createTypedArrayList(CitiesBean.CREATOR);
    }

    public static final Parcelable.Creator<AddressBean> CREATOR = new Parcelable.Creator<AddressBean>() {
        @Override
        public AddressBean createFromParcel(Parcel source) {
            return new AddressBean(source);
        }

        @Override
        public AddressBean[] newArray(int size) {
            return new AddressBean[size];
        }
    };

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
