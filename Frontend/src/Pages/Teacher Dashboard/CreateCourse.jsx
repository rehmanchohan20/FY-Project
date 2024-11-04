import { useState } from 'react';

function CreateCourse() {
    const [courseName, setCourseName] = useState('');
    const [courseDescription, setCourseDescription] = useState('');
    const [uploadedFiles, setUploadedFiles] = useState([]);

    const handleCourseNameChange = (event) => {
        setCourseName(event.target.value);
    };

    const handleCourseDescriptionChange = (event) => {
        setCourseDescription(event.target.value);
    };

    const handleFileDrop = (event) => {
        const files = event.dataTransfer.files;
        setUploadedFiles([...uploadedFiles, ...files]);
    };

    const handleFileInputChange = (event) => {
        const files = event.target.files;
        setUploadedFiles([...uploadedFiles, ...files]);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        // Handle form submission logic here
        console.log('Course Name:', courseName);
        console.log('Course Description:', courseDescription);
        console.log('Uploaded Files:', uploadedFiles);
    };

    return (
        <div className="container mx-auto p-4">
            <h1 className="text-3xl font-bold text-gray-800 mb-4">Create Course</h1>
            <form onSubmit={handleSubmit} className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
                <div className="mb-4">
                    <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="course-name">
                        Course Name
                    </label>
                    <input
                        className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                        id="course-name"
                        type="text"
                        placeholder="Enter Course Name"
                        value={courseName}
                        onChange={handleCourseNameChange}
                    />
                </div>
                <div className="mb-4">
                    <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="course-description">
                        Course Description
                    </label>
                    <textarea
                        className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                        id="course-description"
                        placeholder="Enter Course Description"
                        value={courseDescription}
                        onChange={handleCourseDescriptionChange}
                    />
                </div>
                <div className="mb-4">
                    <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="uploaded-files">
                        Upload Files
                    </label>
                    <div
                        className="flex justify-center px-6 pt-5 pb-6 border-2 border-gray-300 border-dashed rounded-md"
                        onDragOver={(event) => event.preventDefault()}
                        onDrop={handleFileDrop}
                    >
                        <div className="space-y-1 text-center">
                            <svg
                                className="mx-auto h-12 w-12 text-gray-400"
                                stroke="currentColor"
                                fill="none"
                                viewBox="0 0 48 48"
                            >
                                <path
                                    d="M28 8H12a4 4 0 0 0-4 4v20m32-12v8m0 0h-4m4 0h-4m4 0h-4m4 0h-4m4 0h-4m4 0h-4m4 0h-4v-4m0 0h-4"
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                />
                            </svg>
                            <div className="flex text-sm text-gray-600">
                                <label
                                    className="relative cursor-pointer bg-white rounded-md font-medium text-indigo-600 hover:text-indigo-900 focus-within:outline-none focus-within:shadow-outline-blue"
                                    htmlFor="uploaded-files"
                                >
                                    <span>Upload a file</span>
                                    <input
                                        id="uploaded-files"
                                        type="file"
                                        multiple
                                        onChange={handleFileInputChange}
                                        className="sr-only"
                                    />
                                </label>
                                <p className="pl-1">or drag and drop</p>
                            </div>
                            <p className="text-xs text-gray-500">
                                PNG, JPG, GIF up to 10MB
                            </p>
                        </div>
                    </div>
                </div>
                <button
                    className="bg-orange-500 hover:bg-orange-700 text-white font-bold py-2 px-4 rounded"
                    type="submit"
                >
                    Create Course
                </button>
            </form>
        </div>
    );
}

export default CreateCourse;