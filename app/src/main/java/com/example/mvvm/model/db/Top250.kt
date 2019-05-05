package com.example.mvvm.model.db

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "top250", indices = [Index(value = ["title"], unique = true)])
@TypeConverters(Top250.SubjectsBeanConverters::class)
class Top250 {
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
    var count: Int = 0
    var start: Int = 0
    var total: Int = 0
    var title: String? = null
    var subjects: List<SubjectsBean>? = null

    class SubjectsBean {
        var rating: RatingBean? = null
        var title: String? = null
        var collect_count: Int = 0
        var original_title: String? = null
        var subtype: String? = null
        var year: String? = null
        var images: ImagesBean? = null
        var alt: String? = null
        var id: String? = null
        var genres: List<String>? = null
        var casts: List<CastsBean>? = null
        var directors: List<DirectorsBean>? = null

        class RatingBean {
            var max: Int = 0
            var average: Double = 0.toDouble()
            var stars: String? = null
            var min: Int = 0
        }

        class ImagesBean {
            var small: String? = null
            var large: String? = null
            var medium: String? = null
        }

        class CastsBean {
            var alt: String? = null
            var avatars: AvatarsBean? = null
            var name: String? = null
            var id: String? = null

            class AvatarsBean {
                var small: String? = null
                var large: String? = null
                var medium: String? = null
            }
        }

        class DirectorsBean {
            var alt: String? = null
            var avatars: AvatarsBeanX? = null
            var name: String? = null
            var id: String? = null

            class AvatarsBeanX {
                var small: String? = null
                var large: String? = null
                var medium: String? = null
            }
        }
    }

    class SubjectsBeanConverters{
        @TypeConverter
        fun stringToObject(value: String): List<SubjectsBean> {
            val listType = object : TypeToken<List<SubjectsBean>>() {
            }.type
            return Gson().fromJson(value, listType)
        }

        @TypeConverter
        fun objectToString(list: List<SubjectsBean>): String {
            val gson = Gson()
            return gson.toJson(list)
        }
    }
}
