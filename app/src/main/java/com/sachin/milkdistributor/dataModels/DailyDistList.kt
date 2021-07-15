package com.sachin.milkdistributor.dataModels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DailyDistList : ArrayList<DailyDist>(), Parcelable