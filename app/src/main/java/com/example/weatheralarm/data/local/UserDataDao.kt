package com.example.weatheralarm.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weatheralarm.data.model.userData

@Dao
interface userDataDao {

    // 데이터 베이스 불러오기
    @Query("SELECT * from timeTable ORDER BY user ASC")
    fun getAll(): LiveData<List<userData>>

    // 데이터 추가
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: userData)

    // 데이터 전체 삭제
    @Query("DELETE FROM timeTable")
    fun deleteAll()

    // 데이터 업데이트
    @Update
    fun update(entity: userData);

    // db 삭제
    @Delete
    fun delete(entity: userData);

}