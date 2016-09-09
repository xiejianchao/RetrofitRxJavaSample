package com.github.sample.retrofitrxjavasample.model;

import java.util.List;

/**
 * Created by xiejc on 16/9/9.
 */
public class MovieModel {


    private int count;
    private int start;
    private int total;
    private String title;

    private List<SubjectsEntity> subjects;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SubjectsEntity> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectsEntity> subjects) {
        this.subjects = subjects;
    }

    public static class SubjectsEntity {
        /**
         * max : 10
         * average : 9.6
         * stars : 50
         * min : 0
         */

        private RatingEntity rating;
        private String title;
        private int collect_count;
        private String original_title;
        private String subtype;
        private String year;
        /**
         * small : https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p480747492.jpg
         * large : https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p480747492.jpg
         * medium : https://img3.doubanio.com/view/movie_poster_cover/spst/public/p480747492.jpg
         */

        private ImagesEntity images;
        private String alt;
        private String id;
        private List<String> genres;
        /**
         * alt : https://movie.douban.com/celebrity/1054521/
         * avatars : {"small":"https://img3.doubanio.com/img/celebrity/small/17525.jpg",
         * "large":"https://img3.doubanio.com/img/celebrity/large/17525.jpg",
         * "medium":"https://img3.doubanio.com/img/celebrity/medium/17525.jpg"}
         * name : 蒂姆·罗宾斯
         * id : 1054521
         */

        private List<CastsEntity> casts;
        /**
         * alt : https://movie.douban.com/celebrity/1047973/
         * avatars : {"small":"https://img3.doubanio.com/img/celebrity/small/230.jpg",
         * "large":"https://img3.doubanio.com/img/celebrity/large/230.jpg","medium":"https://img3
         * .doubanio.com/img/celebrity/medium/230.jpg"}
         * name : 弗兰克·德拉邦特
         * id : 1047973
         */

        private List<DirectorsEntity> directors;

        public RatingEntity getRating() {
            return rating;
        }

        public void setRating(RatingEntity rating) {
            this.rating = rating;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCollect_count() {
            return collect_count;
        }

        public void setCollect_count(int collect_count) {
            this.collect_count = collect_count;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public String getSubtype() {
            return subtype;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public ImagesEntity getImages() {
            return images;
        }

        public void setImages(ImagesEntity images) {
            this.images = images;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<String> getGenres() {
            return genres;
        }

        public void setGenres(List<String> genres) {
            this.genres = genres;
        }

        public List<CastsEntity> getCasts() {
            return casts;
        }

        public void setCasts(List<CastsEntity> casts) {
            this.casts = casts;
        }

        public List<DirectorsEntity> getDirectors() {
            return directors;
        }

        public void setDirectors(List<DirectorsEntity> directors) {
            this.directors = directors;
        }

        public static class RatingEntity {
            private int max;
            private double average;
            private String stars;
            private int min;

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public double getAverage() {
                return average;
            }

            public void setAverage(double average) {
                this.average = average;
            }

            public String getStars() {
                return stars;
            }

            public void setStars(String stars) {
                this.stars = stars;
            }

            public int getMin() {
                return min;
            }

            public void setMin(int min) {
                this.min = min;
            }

            @Override
            public String toString() {
                return "RatingEntity{" +
                        "max=" + max +
                        ", average=" + average +
                        ", stars='" + stars + '\'' +
                        ", min=" + min +
                        '}';
            }
        }

        public static class ImagesEntity {
            private String small;
            private String large;
            private String medium;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }

            @Override
            public String toString() {
                return "ImagesEntity{" +
                        "small='" + small + '\'' +
                        ", large='" + large + '\'' +
                        ", medium='" + medium + '\'' +
                        '}';
            }
        }

        public static class CastsEntity {
            private String alt;
            /**
             * small : https://img3.doubanio.com/img/celebrity/small/17525.jpg
             * large : https://img3.doubanio.com/img/celebrity/large/17525.jpg
             * medium : https://img3.doubanio.com/img/celebrity/medium/17525.jpg
             */

            private AvatarsEntity avatars;
            private String name;
            private String id;

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public AvatarsEntity getAvatars() {
                return avatars;
            }

            public void setAvatars(AvatarsEntity avatars) {
                this.avatars = avatars;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public static class AvatarsEntity {
                private String small;
                private String large;
                private String medium;

                public String getSmall() {
                    return small;
                }

                public void setSmall(String small) {
                    this.small = small;
                }

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public String getMedium() {
                    return medium;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }
            }
        }

        public static class DirectorsEntity {
            private String alt;
            /**
             * small : https://img3.doubanio.com/img/celebrity/small/230.jpg
             * large : https://img3.doubanio.com/img/celebrity/large/230.jpg
             * medium : https://img3.doubanio.com/img/celebrity/medium/230.jpg
             */

            private AvatarsEntity avatars;
            private String name;
            private String id;

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public AvatarsEntity getAvatars() {
                return avatars;
            }

            public void setAvatars(AvatarsEntity avatars) {
                this.avatars = avatars;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public static class AvatarsEntity {
                private String small;
                private String large;
                private String medium;

                public String getSmall() {
                    return small;
                }

                public void setSmall(String small) {
                    this.small = small;
                }

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public String getMedium() {
                    return medium;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }

                @Override
                public String toString() {
                    return "AvatarsEntity{" +
                            "small='" + small + '\'' +
                            ", large='" + large + '\'' +
                            ", medium='" + medium + '\'' +
                            '}';
                }
            }
        }

        @Override
        public String toString() {
            return "SubjectsEntity{" +
                    "rating=" + rating +
                    ", title='" + title + '\'' +
                    ", collect_count=" + collect_count +
                    ", original_title='" + original_title + '\'' +
                    ", subtype='" + subtype + '\'' +
                    ", year='" + year + '\'' +
                    ", images=" + images +
                    ", alt='" + alt + '\'' +
                    ", id='" + id + '\'' +
                    ", genres=" + genres +
                    ", casts=" + casts +
                    ", directors=" + directors +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MovieModel{" +
                "count=" + count +
                ", start=" + start +
                ", total=" + total +
                ", title='" + title + '\'' +
                ", subjects=" + subjects +
                '}';
    }
}
