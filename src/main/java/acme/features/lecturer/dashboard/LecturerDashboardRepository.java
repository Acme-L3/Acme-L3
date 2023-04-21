/*
 * AdministratorDashboardRepository.java
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

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entitites.lecture.LectureType;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturerDashboardRepository extends AbstractRepository {

	@Query("select count(l) from Lecture l where l.lectureType = :type")
	Integer findLecturesByType(LectureType type);

	@Query("select avg(l.estimateLearningTime) from Lecture l")
	Double getAverageLearningTimeLectures();
	@Query("select stddev(l.estimateLearningTime) from Lecture l")
	Double getDesviationLearningTimeLectures();
	@Query("select max(l.estimateLearningTime) from Lecture l")
	Double getMaxLearningTimeLectures();
	@Query("select min(l.estimateLearningTime) from Lecture l")
	Double getMinLearningTimeLectures();

	@Query(value = "select avg(plus) from (select sum(l.estimate_learning_time) as plus from lecture l group by l.course_id) as r", nativeQuery = true)
	Double getAverageLearningTimeCourses();
	@Query(value = "select stddev(plus) from (select sum(l.estimate_learning_time) as plus from lecture l group by l.course_id) as r", nativeQuery = true)
	Double getDesviationLearningTimeCourses();
	@Query(value = "select min(plus) from (select sum(l.estimate_learning_time) as plus from lecture l group by l.course_id) as r", nativeQuery = true)
	Double getMaxLearningTimeCourses();
	@Query(value = "select max(plus) from (select sum(l.estimate_learning_time) as plus from lecture l group by l.course_id) as r", nativeQuery = true)
	Double getMinLearningTimeCourses();

}
