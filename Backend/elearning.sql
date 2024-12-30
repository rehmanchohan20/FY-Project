-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 23, 2024 at 08:08 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `elearning`
--

-- --------------------------------------------------------

--
-- Table structure for table `assignment`
--

CREATE TABLE `assignment` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` enum('Admin','Self','System','Teacher') NOT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `assignment_path` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `module_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `assignment`
--

INSERT INTO `assignment` (`id`, `created_at`, `created_by`, `update_at`, `assignment_path`, `description`, `title`, `module_id`) VALUES
(1, '2024-12-14 09:27:14.000000', 'Self', '2024-12-14 09:27:14.000000', 'http://localhost/moduleAssignments/1734150434464_Muhammad Rehman 586-2021 Assignment 02 ML.pdf', 'adasdd', 'dasds', 3),
(2, '2024-12-22 09:47:44.000000', 'Self', '2024-12-22 09:47:44.000000', 'http://localhost/moduleAssignments/1734842864571_download-teacher-assignment.pdf', 'TESTING', 'TESTING', 4),
(3, '2024-12-22 18:41:20.000000', 'Self', '2024-12-22 18:41:20.000000', 'http://localhost/moduleAssignments/1734874880517_IGEP FYP II mid presentation .pptx', 'JDLKSAJDALSKDA', 'nadnalk', 6),
(4, '2024-12-23 12:04:54.000000', 'Self', '2024-12-23 12:04:54.000000', 'http://localhost/moduleAssignments/1734937494746_IGEP FYP II mid presentation .pptx', 'etete', 'tret', 14);

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` enum('Admin','Self','System','Teacher') NOT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `category` enum('Design','DigitalMarketing','Others','Programming') DEFAULT NULL,
  `descriptions` varchar(255) DEFAULT NULL,
  `locked` bit(1) NOT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `thumbnail` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `teacher_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`id`, `created_at`, `created_by`, `update_at`, `category`, `descriptions`, `locked`, `status`, `thumbnail`, `title`, `teacher_id`) VALUES
(1, '2024-12-14 09:15:24.000000', 'Teacher', '2024-12-14 09:15:43.000000', 'Programming', 'Java Testing Course', b'1', 0, 'http://localhost/ipegThumbnails/8c451eda-cf43-4c72-93f7-a9d12e1d8fdf.jpg', 'Java Testing Course', 1),
(2, '2024-12-14 09:25:58.000000', 'Teacher', '2024-12-14 09:26:12.000000', 'Programming', 'springboot backend code', b'1', 0, 'http://localhost/ipegThumbnails/15ab8ea9-f788-4f54-9ff4-672636a2524a.jpg', 'springboot coding', 1),
(3, '2024-12-22 09:44:42.000000', 'Teacher', '2024-12-22 09:44:51.000000', 'Programming', 'testing', b'1', 0, 'http://localhost/ipegThumbnails/a88d50c5-b140-4324-aa7d-fa855d506684.jpg', 'testing', 1),
(4, '2024-12-22 18:39:42.000000', 'Teacher', '2024-12-22 18:39:49.000000', 'Programming', 'java', b'1', 0, 'http://localhost/ipegThumbnails/8a3f34ef-0507-4016-8cdc-bec9c4e7f74b.jpg', 'java', 1),
(5, '2024-12-22 18:41:37.000000', 'Teacher', '2024-12-22 18:41:44.000000', 'Design', 'CADASD', b'1', 0, 'http://localhost/ipegThumbnails/8f7d7c7b-094f-4e27-a1a7-0288693fe800.jpg', 'SADDASDASD', 1),
(6, '2024-12-23 07:00:09.000000', 'Teacher', '2024-12-23 07:00:20.000000', 'Design', 'Buy', b'1', 0, 'http://localhost/ipegThumbnails/e62062e4-fa53-4d41-b4e0-767e9e12976a.jpg', 'Hello', 1),
(7, '2024-12-23 07:12:58.000000', 'Teacher', '2024-12-23 07:13:04.000000', 'Design', 'hajkshasjkhdk', b'1', 0, 'http://localhost/ipegThumbnails/8dd533e7-abab-4296-8d35-521962f20b4c.jpg', 'hsdjk', 1),
(8, '2024-12-23 12:04:08.000000', 'Teacher', '2024-12-23 12:04:16.000000', 'Design', 'retretre', b'1', 0, 'http://localhost/ipegThumbnails/d1cc2631-ae46-47d7-856d-7dcf9b3b954f.jpg', 'tretet', 1);

-- --------------------------------------------------------

--
-- Table structure for table `course_guidance`
--

CREATE TABLE `course_guidance` (
  `guidance_id` bigint(20) NOT NULL,
  `course_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `course_module`
--

CREATE TABLE `course_module` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` enum('Admin','Self','System','Teacher') NOT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `heading` varchar(255) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `course_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `course_module`
--

INSERT INTO `course_module` (`id`, `created_at`, `created_by`, `update_at`, `description`, `heading`, `priority`, `course_id`) VALUES
(1, '2024-12-14 09:16:27.000000', 'Teacher', '2024-12-14 09:16:27.000000', 'Java Testing Module ', 'Java Testing Module 1', 1, 1),
(2, '2024-12-14 09:16:27.000000', 'Teacher', '2024-12-14 09:16:27.000000', 'Java Testing Module 2', 'Java Testing Module 2', 2, 1),
(3, '2024-12-14 09:26:22.000000', 'Teacher', '2024-12-14 09:26:22.000000', 'testing springboot', 'rehman', 1, 2),
(4, '2024-12-22 09:45:07.000000', 'Teacher', '2024-12-22 09:45:07.000000', 'testing', 'testing', 1, 3),
(5, '2024-12-22 09:45:07.000000', 'Teacher', '2024-12-22 09:45:07.000000', 'testing 2', 'testing 2', 2, 3),
(6, '2024-12-22 18:39:58.000000', 'Teacher', '2024-12-22 18:39:58.000000', 'java', 'java', 1, 4),
(7, '2024-12-22 18:39:58.000000', 'Teacher', '2024-12-22 18:39:58.000000', 'java', 'java', 2, 4),
(8, '2024-12-22 18:41:55.000000', 'Teacher', '2024-12-22 18:41:55.000000', 'ADSADS', 'DASSDAS', 1, 5),
(9, '2024-12-22 18:41:55.000000', 'Teacher', '2024-12-22 18:41:55.000000', 'DASADA', 'SADADAS', 2, 5),
(10, '2024-12-23 07:00:28.000000', 'Teacher', '2024-12-23 07:00:28.000000', 'jkashkjhd', 'jhdasjk', 1, 6),
(11, '2024-12-23 07:00:28.000000', 'Teacher', '2024-12-23 07:00:28.000000', 'adadad', 'dasdd', 2, 6),
(12, '2024-12-23 07:13:08.000000', 'Teacher', '2024-12-23 07:13:08.000000', 'asdasdas', 'dsf', 1, 7),
(13, '2024-12-23 07:13:08.000000', 'Teacher', '2024-12-23 07:13:08.000000', 'dasda', 'dasdsa', 2, 7),
(14, '2024-12-23 12:04:17.000000', 'Teacher', '2024-12-23 12:04:17.000000', 'etret', 'tret', 1, 8);

-- --------------------------------------------------------

--
-- Table structure for table `course_module_lesson`
--

CREATE TABLE `course_module_lesson` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` enum('Admin','Self','System','Teacher') NOT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `duration` varchar(255) DEFAULT NULL,
  `featured_lesson` varchar(255) DEFAULT NULL,
  `is_content_lock` bit(1) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `course_module` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `course_module_lesson`
--

INSERT INTO `course_module_lesson` (`id`, `created_at`, `created_by`, `update_at`, `description`, `duration`, `featured_lesson`, `is_content_lock`, `priority`, `status`, `title`, `course_module`) VALUES
(1, '2024-12-14 09:17:55.000000', 'Teacher', '2024-12-14 09:17:55.000000', 'Java Testing Module 1', '00:00:09', 'No', b'1', 1, 'Active', 'Java Testing Module 1', 1),
(2, '2024-12-14 09:17:55.000000', 'Teacher', '2024-12-14 09:17:55.000000', 'Java Testing Module 1', '00:00:06', 'No', b'1', 1, 'Active', 'Java Testing Module 1', 2),
(3, '2024-12-14 09:26:46.000000', 'Teacher', '2024-12-14 09:26:46.000000', 'dsadas', '00:00:07', 'No', b'1', 1, 'Active', 'eawda', 3),
(4, '2024-12-22 09:46:58.000000', 'Teacher', '2024-12-22 09:46:58.000000', 'TESTING', '00:00:02', 'No', b'1', 1, 'Active', 'testibng', 4),
(5, '2024-12-22 09:46:58.000000', 'Teacher', '2024-12-22 09:46:58.000000', 'TESTING', '00:00:00', 'No', b'1', 1, 'Active', 'TESTING', 5),
(6, '2024-12-22 18:40:39.000000', 'Teacher', '2024-12-22 18:40:39.000000', 'hh', '00:00:00', 'No', b'1', 1, 'Active', 'hdskjdh', 6),
(7, '2024-12-22 18:40:39.000000', 'Teacher', '2024-12-22 18:40:39.000000', 'shdjkahdkjas', '00:00:04', 'No', b'1', 1, 'Active', 'dsjahdkjh', 7),
(8, '2024-12-22 18:42:24.000000', 'Teacher', '2024-12-22 18:42:24.000000', 'H\\', '00:00:00', 'No', b'1', 1, 'Active', 'DAJHDKJ', 8),
(9, '2024-12-22 18:42:24.000000', 'Teacher', '2024-12-22 18:42:24.000000', 'HDASDAS', '00:00:00', 'No', b'1', 1, 'Active', 'DKJDHSAHJ', 9),
(10, '2024-12-23 07:01:01.000000', 'Teacher', '2024-12-23 07:01:01.000000', 'tretrete', '00:00:00', 'No', b'1', 1, 'Active', 'wrete', 10),
(11, '2024-12-23 07:01:01.000000', 'Teacher', '2024-12-23 07:01:01.000000', 'tetete', '00:00:00', 'No', b'1', 2, 'Active', 'trete', 10),
(12, '2024-12-23 07:01:01.000000', 'Teacher', '2024-12-23 07:01:01.000000', 'tete', '00:00:00', 'No', b'1', 1, 'Active', 'trete', 11),
(13, '2024-12-23 07:02:50.000000', 'Teacher', '2024-12-23 07:02:50.000000', 'tretrete', '00:00:00', 'No', b'1', 1, 'Active', 'wrete', 10),
(14, '2024-12-23 07:02:50.000000', 'Teacher', '2024-12-23 07:02:50.000000', 'tetete', '00:00:00', 'No', b'1', 2, 'Active', 'trete', 10),
(15, '2024-12-23 07:02:50.000000', 'Teacher', '2024-12-23 07:02:50.000000', 'tete', '00:00:00', 'No', b'1', 1, 'Active', 'trete', 11),
(16, '2024-12-23 07:06:47.000000', 'Teacher', '2024-12-23 07:06:47.000000', 'tretrete', '00:00:00', 'No', b'1', 1, 'Active', 'wrete', 10),
(17, '2024-12-23 07:06:47.000000', 'Teacher', '2024-12-23 07:06:47.000000', 'tetete', '00:00:00', 'No', b'1', 2, 'Active', 'trete', 10),
(18, '2024-12-23 07:06:47.000000', 'Teacher', '2024-12-23 07:06:47.000000', 'tete', '00:00:00', 'No', b'1', 1, 'Active', 'trete', 11),
(19, '2024-12-23 07:12:31.000000', 'Teacher', '2024-12-23 07:12:31.000000', 'tretrete', '00:00:00', 'No', b'1', 1, 'Active', 'wrete', 10),
(20, '2024-12-23 07:12:31.000000', 'Teacher', '2024-12-23 07:12:31.000000', 'tetete', '00:00:00', 'No', b'1', 2, 'Active', 'trete', 10),
(21, '2024-12-23 07:12:31.000000', 'Teacher', '2024-12-23 07:12:31.000000', 'tete', '00:00:00', 'No', b'1', 1, 'Active', 'trete', 11),
(22, '2024-12-23 07:13:50.000000', 'Teacher', '2024-12-23 07:13:50.000000', 'rwerwe', '00:00:00', 'No', b'1', 1, 'Active', 'dfwe', 12),
(23, '2024-12-23 07:13:50.000000', 'Teacher', '2024-12-23 07:13:50.000000', 'wrwerwr', '00:00:00', 'No', b'1', 2, 'Active', 'rew', 12),
(24, '2024-12-23 07:13:50.000000', 'Teacher', '2024-12-23 07:13:50.000000', 'rwerwe', '00:00:00', 'No', b'1', 1, 'Active', 'rewrwe', 13),
(25, '2024-12-23 12:04:29.000000', 'Teacher', '2024-12-23 12:04:29.000000', 'ttertre', '00:00:00', 'No', b'1', 1, 'Active', 'retre', 14),
(26, '2024-12-23 12:04:29.000000', 'Teacher', '2024-12-23 12:04:29.000000', 'tetre', '00:00:00', 'No', b'1', 2, 'Active', 'ter', 14);

-- --------------------------------------------------------

--
-- Table structure for table `course_module_lesson_media`
--

CREATE TABLE `course_module_lesson_media` (
  `module_lession_id` bigint(20) NOT NULL,
  `media_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `course_module_lesson_media`
--

INSERT INTO `course_module_lesson_media` (`module_lession_id`, `media_id`) VALUES
(1, 2),
(2, 3),
(3, 5),
(4, 7),
(5, 8),
(6, 10),
(7, 11),
(8, 13),
(9, 14),
(10, 16),
(11, 17),
(12, 18),
(13, 16),
(14, 17),
(15, 18),
(16, 16),
(17, 17),
(18, 18),
(19, 16),
(20, 17),
(21, 18),
(22, 20),
(23, 21),
(24, 22),
(25, 24),
(26, 24);

-- --------------------------------------------------------

--
-- Table structure for table `course_offer`
--

CREATE TABLE `course_offer` (
  `course_id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` enum('Admin','Self','System','Teacher') NOT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `currency` varchar(255) DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `start_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `course_price`
--

CREATE TABLE `course_price` (
  `course_id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` enum('Admin','Self','System','Teacher') NOT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `currency` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `course_price`
--

INSERT INTO `course_price` (`course_id`, `created_at`, `created_by`, `update_at`, `amount`, `currency`) VALUES
(1, '2024-12-14 09:15:24.000000', 'Teacher', '2024-12-14 09:15:24.000000', 1200, 'PKR'),
(2, '2024-12-14 09:25:58.000000', 'Teacher', '2024-12-14 09:25:58.000000', 1220, 'PKR'),
(3, '2024-12-22 09:44:42.000000', 'Teacher', '2024-12-22 09:44:42.000000', 1200, 'PKR'),
(4, '2024-12-22 18:39:42.000000', 'Teacher', '2024-12-22 18:39:42.000000', 1211, 'PKR'),
(5, '2024-12-22 18:41:37.000000', 'Teacher', '2024-12-22 18:41:37.000000', 213, 'PKR'),
(6, '2024-12-23 07:00:10.000000', 'Teacher', '2024-12-23 07:00:10.000000', 1200, 'PKR'),
(7, '2024-12-23 07:12:58.000000', 'Teacher', '2024-12-23 07:12:58.000000', 5464, 'PKR'),
(8, '2024-12-23 12:04:08.000000', 'Teacher', '2024-12-23 12:04:08.000000', 45423, 'PKR');

-- --------------------------------------------------------

--
-- Table structure for table `course_progress`
--

CREATE TABLE `course_progress` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` enum('Admin','Self','System','Teacher') NOT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `progress_percentage` double DEFAULT NULL,
  `course_module_lesson_id` bigint(20) DEFAULT NULL,
  `student_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `course_progress_completed_modules`
--

CREATE TABLE `course_progress_completed_modules` (
  `course_progress_id` bigint(20) NOT NULL,
  `completed_modules` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `enroll_course`
--

CREATE TABLE `enroll_course` (
  `course_id` bigint(20) NOT NULL,
  `student_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `guidance`
--

CREATE TABLE `guidance` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` enum('Admin','Self','System','Teacher') NOT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  `next_step_recommendation` varchar(255) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `student_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `mcq`
--

CREATE TABLE `mcq` (
  `id` bigint(20) NOT NULL,
  `correct_answer` varchar(255) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `course_module_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `mcq`
--

INSERT INTO `mcq` (`id`, `correct_answer`, `question`, `course_module_id`) VALUES
(1, 'd', 'Java Testing Module 1', 1),
(2, 'dsada', 'dsada', 3),
(3, 'TESTING', 'TESTING', 4),
(4, 'asjdklasjdlkasjd', 'djasldjaslk', 6),
(5, 'DASDASDS', 'DASDSA', 8),
(6, 'DASDASDS', 'DASDSA', 9),
(7, 'etrete', 'trete', 14);

-- --------------------------------------------------------

--
-- Table structure for table `mcq_options`
--

CREATE TABLE `mcq_options` (
  `mcq_id` bigint(20) NOT NULL,
  `options` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `mcq_options`
--

INSERT INTO `mcq_options` (`mcq_id`, `options`) VALUES
(1, 'a'),
(1, 'b'),
(1, 'c'),
(1, 'd'),
(2, 'dsada'),
(2, 'sdasdasd'),
(2, 'dasdas'),
(2, 'adada'),
(3, 'TESTING'),
(3, 'TESTING'),
(3, 'TESTING'),
(3, 'TESTING'),
(4, 'sajdasd'),
(4, 'askjdalksjd'),
(4, 'jlasdjaklj'),
(4, 'jsaldjaslkdj'),
(5, 'DASDAS'),
(5, 'SDADD'),
(5, 'ASDASASD'),
(5, 'ASDAS'),
(6, 'DASDAS'),
(6, 'SDADD'),
(6, 'ASDASASD'),
(6, 'ASDAS'),
(7, 'tretre'),
(7, 'tretre'),
(7, 'tret'),
(7, 'ertret');

-- --------------------------------------------------------

--
-- Table structure for table `media`
--

CREATE TABLE `media` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` enum('Admin','Self','System','Teacher') NOT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `duration` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `media`
--

INSERT INTO `media` (`id`, `created_at`, `created_by`, `update_at`, `duration`, `type`, `url`) VALUES
(1, '2024-12-14 09:15:43.000000', 'Teacher', '2024-12-14 09:15:43.000000', NULL, 'thumbnail', 'http://localhost/ipegThumbnails/8c451eda-cf43-4c72-93f7-a9d12e1d8fdf.jpg'),
(2, '2024-12-14 09:17:08.000000', 'Teacher', '2024-12-14 09:17:08.000000', '00:00:09', 'video', 'http://localhost/ipegvideos/7667e2f0-2f8c-4af5-819d-725b57d31bc3.mp4'),
(3, '2024-12-14 09:17:38.000000', 'Teacher', '2024-12-14 09:17:38.000000', '00:00:06', 'video', 'http://localhost/ipegvideos/2bf3cc4e-fb38-4bcc-a2d7-53f36f857727.mp4'),
(4, '2024-12-14 09:26:12.000000', 'Teacher', '2024-12-14 09:26:12.000000', NULL, 'thumbnail', 'http://localhost/ipegThumbnails/15ab8ea9-f788-4f54-9ff4-672636a2524a.jpg'),
(5, '2024-12-14 09:26:38.000000', 'Teacher', '2024-12-14 09:26:38.000000', '00:00:07', 'video', 'http://localhost/ipegvideos/ed0a8c5b-9eea-45b3-9cd6-04c88b7db2ae.mp4'),
(6, '2024-12-22 09:44:51.000000', 'Teacher', '2024-12-22 09:44:51.000000', NULL, 'thumbnail', 'http://localhost/ipegThumbnails/a88d50c5-b140-4324-aa7d-fa855d506684.jpg'),
(7, '2024-12-22 09:45:44.000000', 'Teacher', '2024-12-22 09:45:44.000000', '00:00:02', 'video', 'http://localhost/ipegvideos/3dbb2c20-d4ad-48a4-9e6d-d58f83ef3029.mp4'),
(8, '2024-12-22 09:46:29.000000', 'Teacher', '2024-12-22 09:46:29.000000', '00:00:00', 'video', 'http://localhost/ipegvideos/d8162769-e98b-4e43-80f1-85e6d15472c8.mp4'),
(9, '2024-12-22 18:39:49.000000', 'Teacher', '2024-12-22 18:39:49.000000', NULL, 'thumbnail', 'http://localhost/ipegThumbnails/8a3f34ef-0507-4016-8cdc-bec9c4e7f74b.jpg'),
(10, '2024-12-22 18:40:09.000000', 'Teacher', '2024-12-22 18:40:09.000000', '00:00:00', 'video', 'http://localhost/ipegvideos/6ef8433a-41de-41ea-b023-336c94dde972.mp4'),
(11, '2024-12-22 18:40:19.000000', 'Teacher', '2024-12-22 18:40:19.000000', '00:00:04', 'video', 'http://localhost/ipegvideos/ddf43614-c399-453b-8053-c2c03dadaed6.mp4'),
(12, '2024-12-22 18:41:43.000000', 'Teacher', '2024-12-22 18:41:43.000000', NULL, 'thumbnail', 'http://localhost/ipegThumbnails/8f7d7c7b-094f-4e27-a1a7-0288693fe800.jpg'),
(13, '2024-12-22 18:42:02.000000', 'Teacher', '2024-12-22 18:42:02.000000', '00:00:00', 'video', 'http://localhost/ipegvideos/d1bf7105-6858-41cf-890e-7e29c01e43f6.mp4'),
(14, '2024-12-22 18:42:06.000000', 'Teacher', '2024-12-22 18:42:06.000000', '00:00:00', 'video', 'http://localhost/ipegvideos/8a201222-b18d-4db1-85d3-2c745c792615.mp4'),
(15, '2024-12-23 07:00:20.000000', 'Teacher', '2024-12-23 07:00:20.000000', NULL, 'thumbnail', 'http://localhost/ipegThumbnails/e62062e4-fa53-4d41-b4e0-767e9e12976a.jpg'),
(16, '2024-12-23 07:00:34.000000', 'Teacher', '2024-12-23 07:00:34.000000', '00:00:00', 'video', 'http://localhost/ipegvideos/fd754f5e-87b0-415f-a73f-4bb5525cf3db.mp4'),
(17, '2024-12-23 07:00:39.000000', 'Teacher', '2024-12-23 07:00:39.000000', '00:00:00', 'video', 'http://localhost/ipegvideos/5bac89d6-81bf-4bca-b360-42ba961e543a.mp4'),
(18, '2024-12-23 07:00:48.000000', 'Teacher', '2024-12-23 07:00:48.000000', '00:00:00', 'video', 'http://localhost/ipegvideos/a15efe47-eaee-47bb-a56d-0b8a871dad31.mp4'),
(19, '2024-12-23 07:13:04.000000', 'Teacher', '2024-12-23 07:13:04.000000', NULL, 'thumbnail', 'http://localhost/ipegThumbnails/8dd533e7-abab-4296-8d35-521962f20b4c.jpg'),
(20, '2024-12-23 07:13:14.000000', 'Teacher', '2024-12-23 07:13:14.000000', '00:00:00', 'video', 'http://localhost/ipegvideos/e894e42f-0675-44ce-8690-1a779b6404f1.mp4'),
(21, '2024-12-23 07:13:32.000000', 'Teacher', '2024-12-23 07:13:32.000000', '00:00:00', 'video', 'http://localhost/ipegvideos/e86d3f40-886d-43f2-b87b-2c477380b8e2.mp4'),
(22, '2024-12-23 07:13:37.000000', 'Teacher', '2024-12-23 07:13:37.000000', '00:00:00', 'video', 'http://localhost/ipegvideos/82e0aced-b85d-4805-96af-0559bf6d4e08.mp4'),
(23, '2024-12-23 12:04:16.000000', 'Teacher', '2024-12-23 12:04:16.000000', NULL, 'thumbnail', 'http://localhost/ipegThumbnails/d1cc2631-ae46-47d7-856d-7dcf9b3b954f.jpg'),
(24, '2024-12-23 12:04:22.000000', 'Teacher', '2024-12-23 12:04:22.000000', '00:00:00', 'video', 'http://localhost/ipegvideos/56e70c3b-c620-488b-8ca2-9452a8dc636c.mp4');

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` enum('Admin','Self','System','Teacher') NOT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `currency` varchar(255) DEFAULT NULL,
  `status` enum('FAILED','PENDING','SUCCESS') DEFAULT NULL,
  `transaction_id` varchar(255) DEFAULT NULL,
  `course_id` bigint(20) DEFAULT NULL,
  `student_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `user_id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` enum('Admin','Self','System','Teacher') NOT NULL,
  `update_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`user_id`, `created_at`, `created_by`, `update_at`) VALUES
(2, '2024-12-14 09:29:16.000000', 'Self', '2024-12-14 09:29:16.000000');

-- --------------------------------------------------------

--
-- Table structure for table `teacher`
--

CREATE TABLE `teacher` (
  `user_id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` enum('Admin','Self','System','Teacher') NOT NULL,
  `update_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `teacher`
--

INSERT INTO `teacher` (`user_id`, `created_at`, `created_by`, `update_at`) VALUES
(1, '2024-12-14 09:14:46.000000', 'Self', '2024-12-14 09:14:46.000000');

-- --------------------------------------------------------

--
-- Table structure for table `ticket`
--

CREATE TABLE `ticket` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `resolved_at` datetime(6) DEFAULT NULL,
  `status` enum('CLOSED','OPEN') DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `student_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` enum('Admin','Self','System','Teacher') NOT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `provider` enum('TraditionalLogin','google') DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `is_admin` bit(1) DEFAULT NULL,
  `otp` varchar(255) DEFAULT NULL,
  `otp_generated_time` datetime(6) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `provider_id` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `created_at`, `created_by`, `update_at`, `provider`, `email`, `full_name`, `image`, `is_admin`, `otp`, `otp_generated_time`, `password`, `provider_id`, `username`) VALUES
(1, '2024-12-14 09:14:46.000000', 'Self', '2024-12-14 09:14:46.000000', 'TraditionalLogin', 'awais@gmail.com', 'awais', 'default-profile.png', b'0', NULL, NULL, '$2a$10$bTBRbjDn4QhjgP4GC/k24.BoESyoQ4jqMMgtA2b.WSxx.pMCPShhK', '6a659426-28ce-4efc-b338-f1cb2a704a99', 'awais'),
(2, '2024-12-14 09:29:16.000000', 'Self', '2024-12-14 09:29:16.000000', 'TraditionalLogin', 'anaya@gmail.com', 'anaya', 'default-profile.png', b'0', NULL, NULL, '$2a$10$R8.u.y3WOcR2sorDgq82DumB2Ranmg6o1JSOaKC04CN5B1roOIRJW', 'a6d8eadb-d564-4bbc-8dcf-3537126d7e6a', 'anaya');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `assignment`
--
ALTER TABLE `assignment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKdpcydqyhnga0s10rewe6p7buc` (`module_id`);

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKsybhlxoejr4j3teomm5u2bx1n` (`teacher_id`);

--
-- Indexes for table `course_guidance`
--
ALTER TABLE `course_guidance`
  ADD PRIMARY KEY (`course_id`,`guidance_id`),
  ADD KEY `FKm6t9yksam5w1yva2g0d2omrnv` (`guidance_id`);

--
-- Indexes for table `course_module`
--
ALTER TABLE `course_module`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKkge7sg0xxyo0sxgfelpavhjdj` (`course_id`);

--
-- Indexes for table `course_module_lesson`
--
ALTER TABLE `course_module_lesson`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKo1kubkd6lcbdw0co1sshytmml` (`course_module`);

--
-- Indexes for table `course_module_lesson_media`
--
ALTER TABLE `course_module_lesson_media`
  ADD PRIMARY KEY (`module_lession_id`,`media_id`),
  ADD KEY `FKg8ed4w61jqe0i8qt3rgkwdhrv` (`media_id`);

--
-- Indexes for table `course_offer`
--
ALTER TABLE `course_offer`
  ADD PRIMARY KEY (`course_id`);

--
-- Indexes for table `course_price`
--
ALTER TABLE `course_price`
  ADD PRIMARY KEY (`course_id`);

--
-- Indexes for table `course_progress`
--
ALTER TABLE `course_progress`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKd257qmwgcha7o47xt4shdtlq5` (`course_module_lesson_id`),
  ADD KEY `FK30d2g6esii6j2ypudlmvd6w0a` (`student_id`);

--
-- Indexes for table `course_progress_completed_modules`
--
ALTER TABLE `course_progress_completed_modules`
  ADD KEY `FKn0y0t7wu4vb5wbuotljfimmjq` (`course_progress_id`);

--
-- Indexes for table `enroll_course`
--
ALTER TABLE `enroll_course`
  ADD PRIMARY KEY (`course_id`,`student_id`),
  ADD KEY `FKcip0i6evvbqj68bfxqcc4237i` (`student_id`);

--
-- Indexes for table `guidance`
--
ALTER TABLE `guidance`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKqfa1v0es1gjcuu3kf85hkvyl` (`student_id`);

--
-- Indexes for table `mcq`
--
ALTER TABLE `mcq`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKn8i60hqh2rxuhpu6du515jykh` (`course_module_id`);

--
-- Indexes for table `mcq_options`
--
ALTER TABLE `mcq_options`
  ADD KEY `FKomni038tmd8f5y2vx4lvk9lj2` (`mcq_id`);

--
-- Indexes for table `media`
--
ALTER TABLE `media`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK10va7eoiu5bmbxcfoakxx9omn` (`course_id`),
  ADD KEY `FKq0mpbhvyrwyggk1gwjams69wf` (`student_id`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `teacher`
--
ALTER TABLE `teacher`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `ticket`
--
ALTER TABLE `ticket`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK21tryhx6fi58vsfu5mgs0x2jr` (`student_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`),
  ADD UNIQUE KEY `UKsb8bbouer5wak8vyiiy4pf2bx` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `assignment`
--
ALTER TABLE `assignment`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `course`
--
ALTER TABLE `course`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `course_module`
--
ALTER TABLE `course_module`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `course_module_lesson`
--
ALTER TABLE `course_module_lesson`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `course_progress`
--
ALTER TABLE `course_progress`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `guidance`
--
ALTER TABLE `guidance`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `mcq`
--
ALTER TABLE `mcq`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `media`
--
ALTER TABLE `media`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `ticket`
--
ALTER TABLE `ticket`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `assignment`
--
ALTER TABLE `assignment`
  ADD CONSTRAINT `FKdpcydqyhnga0s10rewe6p7buc` FOREIGN KEY (`module_id`) REFERENCES `course_module` (`id`);

--
-- Constraints for table `course`
--
ALTER TABLE `course`
  ADD CONSTRAINT `FKsybhlxoejr4j3teomm5u2bx1n` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`user_id`);

--
-- Constraints for table `course_guidance`
--
ALTER TABLE `course_guidance`
  ADD CONSTRAINT `FK1hdqukf16784hjbvc2hi0hr4` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
  ADD CONSTRAINT `FKm6t9yksam5w1yva2g0d2omrnv` FOREIGN KEY (`guidance_id`) REFERENCES `guidance` (`id`);

--
-- Constraints for table `course_module`
--
ALTER TABLE `course_module`
  ADD CONSTRAINT `FKkge7sg0xxyo0sxgfelpavhjdj` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`);

--
-- Constraints for table `course_module_lesson`
--
ALTER TABLE `course_module_lesson`
  ADD CONSTRAINT `FKo1kubkd6lcbdw0co1sshytmml` FOREIGN KEY (`course_module`) REFERENCES `course_module` (`id`);

--
-- Constraints for table `course_module_lesson_media`
--
ALTER TABLE `course_module_lesson_media`
  ADD CONSTRAINT `FK3l4g6fm67677e13dl7lx89owl` FOREIGN KEY (`module_lession_id`) REFERENCES `course_module_lesson` (`id`),
  ADD CONSTRAINT `FKg8ed4w61jqe0i8qt3rgkwdhrv` FOREIGN KEY (`media_id`) REFERENCES `media` (`id`);

--
-- Constraints for table `course_offer`
--
ALTER TABLE `course_offer`
  ADD CONSTRAINT `FK93lvdr71rwqwlgw5qk63nrl50` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`);

--
-- Constraints for table `course_price`
--
ALTER TABLE `course_price`
  ADD CONSTRAINT `FK9qy75q9hyu79mfx4i80rbd57` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`);

--
-- Constraints for table `course_progress`
--
ALTER TABLE `course_progress`
  ADD CONSTRAINT `FK30d2g6esii6j2ypudlmvd6w0a` FOREIGN KEY (`student_id`) REFERENCES `student` (`user_id`),
  ADD CONSTRAINT `FKd257qmwgcha7o47xt4shdtlq5` FOREIGN KEY (`course_module_lesson_id`) REFERENCES `course_module_lesson` (`id`);

--
-- Constraints for table `course_progress_completed_modules`
--
ALTER TABLE `course_progress_completed_modules`
  ADD CONSTRAINT `FKn0y0t7wu4vb5wbuotljfimmjq` FOREIGN KEY (`course_progress_id`) REFERENCES `course_progress` (`id`);

--
-- Constraints for table `enroll_course`
--
ALTER TABLE `enroll_course`
  ADD CONSTRAINT `FKcip0i6evvbqj68bfxqcc4237i` FOREIGN KEY (`student_id`) REFERENCES `course` (`id`),
  ADD CONSTRAINT `FKpbx4pb7oce1rsby7yherlj16i` FOREIGN KEY (`course_id`) REFERENCES `student` (`user_id`);

--
-- Constraints for table `guidance`
--
ALTER TABLE `guidance`
  ADD CONSTRAINT `FKqfa1v0es1gjcuu3kf85hkvyl` FOREIGN KEY (`student_id`) REFERENCES `student` (`user_id`);

--
-- Constraints for table `mcq`
--
ALTER TABLE `mcq`
  ADD CONSTRAINT `FKn8i60hqh2rxuhpu6du515jykh` FOREIGN KEY (`course_module_id`) REFERENCES `course_module` (`id`);

--
-- Constraints for table `mcq_options`
--
ALTER TABLE `mcq_options`
  ADD CONSTRAINT `FKomni038tmd8f5y2vx4lvk9lj2` FOREIGN KEY (`mcq_id`) REFERENCES `mcq` (`id`);

--
-- Constraints for table `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `FK10va7eoiu5bmbxcfoakxx9omn` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
  ADD CONSTRAINT `FKq0mpbhvyrwyggk1gwjams69wf` FOREIGN KEY (`student_id`) REFERENCES `student` (`user_id`);

--
-- Constraints for table `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `FKk5m148xqefonqw7bgnpm0snwj` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `teacher`
--
ALTER TABLE `teacher`
  ADD CONSTRAINT `FKpb6g6pahj1mr2ijg92r7m1xlh` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `ticket`
--
ALTER TABLE `ticket`
  ADD CONSTRAINT `FK21tryhx6fi58vsfu5mgs0x2jr` FOREIGN KEY (`student_id`) REFERENCES `student` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
