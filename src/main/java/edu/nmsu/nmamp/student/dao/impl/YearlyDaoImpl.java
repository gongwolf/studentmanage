package edu.nmsu.nmamp.student.dao.impl;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import edu.nmsu.nmamp.student.dao.Schemacode;
import edu.nmsu.nmamp.student.model.YearlyBean;
import edu.nmsu.nmamp.student.service.ProgramCode;

@Repository("YearlyDaoImpl")

public class YearlyDaoImpl implements Schemacode {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Integer> getSchoolYearList(String ic, int user_id) {
		HashSet<Integer> allyears = new HashSet<Integer>();
		StringBuilder sql = new StringBuilder();
		sql.append("select school_year from application_list where user_id=" + user_id);
		// System.out.println(sql.toString());
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql.toString());
		for (Map<String, Object> row : rows) {
			Date d = (Date) row.get("school_year");
			String year = d.toString().substring(0, 4);
			allyears.add(Integer.parseInt(year));
		}
		System.out.println("==========================");
		sql.setLength(0);
		sql.append("select semester from selfreport_data where user_id=" + user_id);
		// System.out.println(sql.toString());
		rows = jdbcTemplate.queryForList(sql.toString());
		for (Map<String, Object> row : rows) {
			String endYear = (String) row.get("semester");
			endYear = endYear.substring(endYear.lastIndexOf(" ") + 1, endYear.length());
			allyears.add(Integer.parseInt(endYear));
		}
		List<Integer> list = new ArrayList<>(allyears);
		return list;
	}

	public YearlyBean getYearlyBeanInfoByYear(int year, int user_id) {
		YearlyBean yb = new YearlyBean();

		//Get information from applications
		StringBuilder sql = new StringBuilder();
		sql.append("select application_id, program from application_list where user_id=" + user_id
				+ " and school_year='" + year + "'");
		HashMap<Long, String> app_program_map = new HashMap<>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql.toString());
		for (Map<String, Object> row : rows) {
			String program = (String) row.get("program");
			long application_id = (long) row.get("application_id");
			System.out.println(application_id + "  " + program);
			app_program_map.put(application_id, program);
		}

		yb.setInstitution(getInstitutionFromProgramByAppID(app_program_map));
		yb.setMentor(getMentorFromProgramByAppID(app_program_map));
		yb.setDiscipline(getDisciplineFromProgramByAppID(app_program_map));
		yb.setCollege_Level(getCollegeLevelFromProgramByAppID(app_program_map));
		getTransInfoFromProgramByAppID(app_program_map, yb);
		
		//Get information from self-report

		System.out.println("=========================================");

		return yb;
	}

	private void getTransInfoFromProgramByAppID(HashMap<Long, String> app_program_map, YearlyBean yb) {
		for (Map.Entry<Long, String> e : app_program_map.entrySet()) {
			if (e.getValue().equals("TRANS")) {
				String app_table = ProgramCode.TABLE_APPLICATION_DETAIL.get(e.getValue());
				String sql = "select academic_school,academic_transfer_school from " + app_table + " where application_id='" + e.getKey() + "'";

				List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql.toString());
				for (Map<String, Object> row : rows) {
					String academic_from = (String) row.get("academic_school");
					String academic_to = (String) row.get("academic_to");
					yb.setIfTransferred(1);
					yb.setTransferred_from(academic_from);
					yb.setTransferred_to(academic_to);
					break;

				}
			}

		}

	}

	private String getCollegeLevelFromProgramByAppID(HashMap<Long, String> app_program_map) {
		HashSet<String> tlist = new HashSet<>();
		for (Map.Entry<Long, String> e : app_program_map.entrySet()) {
			String app_table = ProgramCode.TABLE_APPLICATION_DETAIL.get(e.getValue());
			String sql = "select academic_year from " + app_table + " where application_id='" + e.getKey() + "'";

			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql.toString());
			for (Map<String, Object> row : rows) {
				String academic_year = (String) row.get("academic_year");
				tlist.add(academic_year);
			}
		}
		List<String> list = new ArrayList<>(tlist);
		String listString = list.stream().map(Object::toString).collect(Collectors.joining(", "));
		System.out.println(listString);
		return listString;
	}

	private String getDisciplineFromProgramByAppID(HashMap<Long, String> app_program_map) {
		HashSet<String> tlist = new HashSet<>();
		for (Map.Entry<Long, String> e : app_program_map.entrySet()) {
			String app_table = ProgramCode.TABLE_APPLICATION_DETAIL.get(e.getValue());
			String sql = "select academic_major,academic_minor from " + app_table + " where application_id='"
					+ e.getKey() + "'";

			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql.toString());
			for (Map<String, Object> row : rows) {
				String major = (String) row.get("academic_major");
				String minor = (String) row.get("academic_minor");
				tlist.add("Major:" + major + " Minor:" + minor);
			}
		}
		List<String> list = new ArrayList<>(tlist);
		String listString = list.stream().map(Object::toString).collect(Collectors.joining(", "));
		System.out.println(listString);
		return listString;
	}

	private String getMentorFromProgramByAppID(HashMap<Long, String> app_program_map) {
		HashSet<String> tlist = new HashSet<>();
		for (Map.Entry<Long, String> e : app_program_map.entrySet()) {
			if (e.getValue().equals("IREP") || e.getValue().equals("URS")) {
				String app_table = ProgramCode.TABLE_APPLICATION_DETAIL.get(e.getValue());
				String sql = "select mentor_first_name,mentor_last_name from " + app_table + " where application_id='"
						+ e.getKey() + "'";

				List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql.toString());
				for (Map<String, Object> row : rows) {
					String f_name = (String) row.get("mentor_first_name");
					String l_name = (String) row.get("mentor_last_name");
					tlist.add(f_name + " " + l_name);
				}
			}

		}
		List<String> list = new ArrayList<>(tlist);
		String listString = list.stream().map(Object::toString).collect(Collectors.joining(", "));
		System.out.println(listString);
		return listString;
	}

	private String getInstitutionFromProgramByAppID(HashMap<Long, String> app_program_map) {
		HashSet<String> tlist = new HashSet<>();
		for (Map.Entry<Long, String> e : app_program_map.entrySet()) {
			String app_table = ProgramCode.TABLE_APPLICATION_DETAIL.get(e.getValue());
			String sql = "select academic_school from " + app_table + " where application_id='" + e.getKey() + "'";

			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql.toString());
			for (Map<String, Object> row : rows) {
				String academic_school = (String) row.get("academic_school");
				tlist.add(ProgramCode.ACADEMIC_SCHOOL.get(academic_school));
			}

		}
		List<String> list = new ArrayList<>(tlist);
		String listString = list.stream().map(Object::toString).collect(Collectors.joining(", "));
		System.out.println(listString);
		return listString;
	}

}
