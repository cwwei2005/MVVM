package com.example.mvvm.db

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "article")
@TypeConverters(Article.ItemsBeanConverters::class)
class Article {

    @PrimaryKey
    var id: Int = 0
    var isIncomplete_results: Boolean = false
    var total_count: Int = 0
    var items: List<ItemsBean>? = null

    class ItemsBean {
        var channel: Int = 0
        var click: Int = 0
        var comments: Int = 0
        var content: String? = null
        var downvote: Int = 0
        var id: Int = 0
        var pubDate: String? = null
        var stow: Int = 0
        var thumbnail: String? = null
        var title: String? = null
        var upvote: Int = 0
        var url: String? = null
        @Embedded
        var user: UserBean? = null

        class UserBean {
            /**
             * face : http://www.jcodecraeer.com/uploads/userup/15185/myface.jpg
             * id : 15185
             * nickname : codeGoogler
             */

            var face: String? = null
            var id: Int = 0
            var nickname: String? = null
        }
    }

    class ItemsBeanConverters{
        @TypeConverter
        fun stringToObject(value: String): List<ItemsBean> {
            val listType = object : TypeToken<List<ItemsBean>>() {
            }.type
            return Gson().fromJson(value, listType)
        }

        @TypeConverter
        fun objectToString(list: List<ItemsBean>): String {
            val gson = Gson()
            return gson.toJson(list)
        }
    }
}