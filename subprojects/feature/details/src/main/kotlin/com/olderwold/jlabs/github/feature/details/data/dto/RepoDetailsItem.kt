package com.olderwold.jlabs.github.feature.details.data.dto

import com.google.gson.annotations.SerializedName

internal data class RepoDetailsItem(
    @SerializedName("author")
    val author: Author? = null,
    @SerializedName("comments_url")
    val commentsUrl: String? = null,
    @SerializedName("commit")
    val commit: Commit? = null,
    @SerializedName("committer")
    val committer: CommitterX? = null,
    @SerializedName("html_url")
    val htmlUrl: String? = null,
    @SerializedName("node_id")
    val nodeId: String? = null,
    @SerializedName("parents")
    val parents: List<Parent>? = null,
    @SerializedName("sha")
    val sha: String? = null,
    @SerializedName("url")
    val url: String? = null
)
