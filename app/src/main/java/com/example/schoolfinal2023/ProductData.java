package com.example.schoolfinal2023;

public class ProductData {

    DataClass data;

    class DataClass {
        String categories;
        String id;


        public String getCategories() {
            return categories;
        }

        public void setCategories(String categories) {
            this.categories = categories;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
