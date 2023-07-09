/*
 * AdministratorDashboardShowService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.lecturer.dashboard;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.lecture.LectureType;
import acme.forms.LecturerDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerDashboardShowService extends AbstractService<Lecturer, LecturerDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		final boolean status = super.getRequest().getPrincipal().hasRole(Lecturer.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		LecturerDashboard dashboard;
		final Map<LectureType, Integer> countOfLecturesByType;
		final Map<String, Double> statisticsLearningTimeLectures;
		final Map<String, Double> statisticsLearningTimeCourses;

		final int id_account = super.getRequest().getPrincipal().getAccountId();
		final Lecturer l = this.repository.findOneLecturerByUserAccountId(id_account);
		final int id = l.getId();

		countOfLecturesByType = new HashMap<>();
		statisticsLearningTimeLectures = new HashMap<>();
		statisticsLearningTimeCourses = new HashMap<>();

		final String lang = super.getRequest().getLocale().getLanguage();
		final String[] methodInEnglish = {
			"Average", "Desviation", "Minimum", "Maximum"
		};
		final String[] methodInSpanish = {
			"Media", "Desviacion", "Mínimo", "Máximo"
		};

		final Integer lectureHandsOn = this.repository.findLecturesByType(LectureType.HANDS_ON, id);
		final Integer lectureTheory = this.repository.findLecturesByType(LectureType.THEORY, id);
		countOfLecturesByType.put(LectureType.HANDS_ON, lectureHandsOn);
		countOfLecturesByType.put(LectureType.THEORY, lectureTheory);

		final Double averageLecture = this.repository.getAverageLearningTimeLectures(id);
		final Double desviationLecture = this.repository.getDesviationLearningTimeLectures(id);
		final Double minLecture = this.repository.getMinLearningTimeLectures(id);
		final Double maxLecture = this.repository.getMaxLearningTimeLectures(id);
		if (!lang.equals("es")) {
			statisticsLearningTimeLectures.put(methodInEnglish[0], averageLecture);
			statisticsLearningTimeLectures.put(methodInEnglish[1], desviationLecture);
			statisticsLearningTimeLectures.put(methodInEnglish[2], minLecture);
			statisticsLearningTimeLectures.put(methodInEnglish[3], maxLecture);
		} else {
			statisticsLearningTimeLectures.put(methodInSpanish[0], averageLecture);
			statisticsLearningTimeLectures.put(methodInSpanish[1], desviationLecture);
			statisticsLearningTimeLectures.put(methodInSpanish[2], minLecture);
			statisticsLearningTimeLectures.put(methodInSpanish[3], maxLecture);
		}

		final Double averageCourse = this.repository.getAverageLearningTimeCourses(id);
		final Double desviationCourse = this.repository.getDesviationLearningTimeCourses(id);
		final Double minCourse = this.repository.getMinLearningTimeCourses(id);
		final Double maxCourse = this.repository.getMaxLearningTimeCourses(id);
		if (!lang.equals("es")) {
			statisticsLearningTimeCourses.put(methodInEnglish[0], averageCourse);
			statisticsLearningTimeCourses.put(methodInEnglish[1], desviationCourse);
			statisticsLearningTimeCourses.put(methodInEnglish[2], minCourse);
			statisticsLearningTimeCourses.put(methodInEnglish[3], maxCourse);
		} else {
			statisticsLearningTimeCourses.put(methodInSpanish[0], averageCourse);
			statisticsLearningTimeCourses.put(methodInSpanish[1], desviationCourse);
			statisticsLearningTimeCourses.put(methodInSpanish[2], minCourse);
			statisticsLearningTimeCourses.put(methodInSpanish[3], maxCourse);
		}

		dashboard = new LecturerDashboard();
		dashboard.setCountOfLecturesByType(countOfLecturesByType);
		dashboard.setStatisticsLearningTimeCourses(statisticsLearningTimeCourses);
		dashboard.setStatisticsLearningTimeLectures(statisticsLearningTimeLectures);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final LecturerDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, //
			"countOfLecturesByType", "statisticsLearningTimeLectures", // 
			"statisticsLearningTimeCourses");

		super.getResponse().setData(tuple);
	}

}
