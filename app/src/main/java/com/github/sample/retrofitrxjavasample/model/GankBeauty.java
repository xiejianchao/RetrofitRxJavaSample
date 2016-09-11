// (c)2016 Flipboard Inc, All Rights Reserved.

package com.github.sample.retrofitrxjavasample.model;

import java.util.List;

public class GankBeauty {


    /**
     * error : false
     * results : [{"_id":"57cc16c9421aa910f56bd8ab","createdAt":"2016-09-04T20:42:49.403Z",
     * "desc":"09-05","publishedAt":"2016-09-05T11:32:16.999Z","source":"chrome","type":"福利",
     * "url":"http://ww1.sinaimg.cn/large/610dc034jw1f7hu7d460oj20u00u075u.jpg","used":true,
     * "who":"daimajia"},{"_id":"57c83167421aa9125fa3edd0","createdAt":"2016-09-01T21:47:19
     * .924Z","desc":"Whatever","publishedAt":"2016-09-02T20:36:28.951Z","source":"chrome",
     * "type":"福利","url":"http://ww1.sinaimg.cn/large/610dc034jw1f7ef7i5m1zj20u011hdjm.jpg",
     * "used":true,"who":"daimajia"}]
     */

    private boolean error;
    /**
     * _id : 57cc16c9421aa910f56bd8ab
     * createdAt : 2016-09-04T20:42:49.403Z
     * desc : 09-05
     * publishedAt : 2016-09-05T11:32:16.999Z
     * source : chrome
     * type : 福利
     * url : http://ww1.sinaimg.cn/large/610dc034jw1f7hu7d460oj20u00u075u.jpg
     * used : true
     * who : daimajia
     */

    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        @Override
        public String toString() {
            return "ResultsBean{" +
                    "_id='" + _id + '\'' +
                    ", createdAt='" + createdAt + '\'' +
                    ", desc='" + desc + '\'' +
                    ", publishedAt='" + publishedAt + '\'' +
                    ", source='" + source + '\'' +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    ", used=" + used +
                    ", who='" + who + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GankBeauty{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
