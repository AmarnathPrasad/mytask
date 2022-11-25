package com.project.mytask.Pojo

import com.google.gson.annotations.SerializedName

class Data {

    @SerializedName("userId")
    var userId: String? = null

    @SerializedName("id")
    var id: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("body")
    var body: String? = null

    constructor(userId: String?, id: String?, title: String?, body: String?) {
        this.userId = userId
        this.id = id
        this.title = title
        this.body = body
    }




}
