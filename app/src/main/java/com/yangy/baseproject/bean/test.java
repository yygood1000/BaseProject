package com.yangy.baseproject.bean;

import java.util.List;

public class test {
    public String story_id;
    public ColorsBean colors;
    public String author;
    public String title;
    public String description;
    public String locale;
    public boolean is_new;
    public String image_url;
    public List<String> genres;
    public List<CharactersBean> characters;
    public List<EpisodesBean> episodes;


    public static class ColorsBean {
        public String subtitle;
        public String background;
        public String description;
        public String button;
        public String stats;

    }

    public static class CharactersBean {
        public String name;
    }

    public static class EpisodesBean {

        public int main_character;
        public int messages_count;
        public String old_app_id;
        public List<MessagesBean> messages;

        public static class MessagesBean {
            public boolean is_climax;
            public int sender;
            public String text;
            public boolean typing;
        }
    }
}
