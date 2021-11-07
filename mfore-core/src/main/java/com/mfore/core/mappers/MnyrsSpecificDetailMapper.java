package com.mfore.core.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.mfore.core.representations.MnyrsSpecificDetail;

public class MnyrsSpecificDetailMapper  implements ResultSetMapper<MnyrsSpecificDetail>{
	public MnyrsSpecificDetail map(int index, ResultSet r,StatementContext ctx)throws SQLException {
		
		return new MnyrsSpecificDetail(
				r.getInt("mnyrs_specific_detail_id"), 
				r.getInt("fk_person_id"),
				r.getDate("birth_date"),
				r.getString("gender"),
				r.getInt("height"),
				r.getInt("weight"),
				r.getString("blood_group")
				);
	}
}
