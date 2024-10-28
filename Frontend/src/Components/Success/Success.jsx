import React from 'react';

const Success = () => {
  return (
    <section className="py-12 bg-white text-center">
      {/* Section Title */}
      <div className="mb-8">
        <h2 className="text-3xl font-semibold text-gray-700">Our Success</h2>
        <p className="mt-2 text-gray-500 italic">
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla vitae efficitur leo.
        </p>
      </div>

      {/* Success Numbers */}
      <div className="grid grid-cols-2 sm:grid-cols-5 gap-8 max-w-5xl mx-auto">
        {/* Item 1 */}
        <div>
          <h3 className="text-4xl font-semibold text-indigo-600">10K+</h3>
          <p className="mt-2 text-gray-600">Students</p>
        </div>

        {/* Item 2 */}
        <div>
          <h3 className="text-4xl font-semibold text-indigo-600">75%</h3>
          <p className="mt-2 text-gray-600">Total success</p>
        </div>

        {/* Item 3 */}
        <div>
          <h3 className="text-4xl font-semibold text-indigo-600">35</h3>
          <p className="mt-2 text-gray-600">Main questions</p>
        </div>

        {/* Item 4 */}
        <div>
          <h3 className="text-4xl font-semibold text-indigo-600">26</h3>
          <p className="mt-2 text-gray-600">Chief experts</p>
        </div>

        {/* Item 5 */}
        <div>
          <h3 className="text-4xl font-semibold text-indigo-600">16</h3>
          <p className="mt-2 text-gray-600">Years of experience</p>
        </div>
      </div>
    </section>
  );
};

export default Success;
