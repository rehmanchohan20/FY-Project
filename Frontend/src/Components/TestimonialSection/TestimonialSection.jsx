import React from "react";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

const testimonials = [
  {
    name: "Gloria Ross",
    review: "Thank you so much for your help. It’s exactly what I’ve been looking for. You won’t regret it, a really good time saver for students.",
    rating: 5,
    date: "23 reviews at iGLBP",
    image: "https://via.placeholder.com/100", // replace with actual image URL
  },
  // Add more testimonial objects as needed
];

const TestimonialSection = () => {
  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 3000,
  };

  return (
    <section className="bg-white py-12">
      <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="lg:grid lg:grid-cols-2 lg:gap-8 lg:items-center">
          {/* Left side - Text Section */}
          <div>
            <h2 className="text-3xl font-bold text-gray-900 sm:text-4xl">What They Say?</h2>
            <p className="mt-4 text-lg leading-6 text-gray-600">
              iGLBP has got more than 100k positive ratings from our users around the world. 
              Some of the students and teachers were greatly helped by the skillline.
            </p>
            <p className="mt-4 text-lg leading-6 text-gray-600">
              Are you too? Please give your assessment.
            </p>
            <button className="mt-6 px-6 py-2 bg-blue-600 text-white rounded-full hover:bg-blue-700">
              Write your assessment
            </button>
          </div>

          {/* Right side - Testimonial Slider */}
          <div className="mt-8 lg:mt-0">
            <Slider {...settings}>
              {testimonials.map((testimonial, index) => (
                <div key={index} className="bg-white p-6 rounded-lg shadow-md">
                  <div className="flex items-center">
                    <img
                      src={testimonial.image}
                      alt={testimonial.name}
                      className="w-16 h-16 rounded-full mr-4"
                    />
                    <div>
                      <p className="text-gray-900 font-bold">{testimonial.name}</p>
                      <p className="text-gray-600 text-sm">{testimonial.date}</p>
                    </div>
                  </div>
                  <p className="mt-4 text-gray-600">{testimonial.review}</p>
                  <div className="mt-2 flex">
                    {Array.from({ length: testimonial.rating }).map((_, idx) => (
                      <svg
                        key={idx}
                        className="w-5 h-5 text-yellow-400"
                        fill="currentColor"
                        viewBox="0 0 20 20"
                      >
                        <path d="M9.049 2.927C9.344 2.186 10.656 2.186 10.951 2.927L12.234 6.197L15.974 6.497C16.765 6.562 17.087 7.575 16.524 8.101L13.879 10.525L14.661 14.23C14.831 15.048 13.899 15.599 13.214 15.138L9.999 13.094L6.785 15.138C6.101 15.599 5.169 15.048 5.339 14.23L6.121 10.525L3.476 8.101C2.913 7.575 3.235 6.562 4.026 6.497L7.766 6.197L9.049 2.927Z" />
                      </svg>
                    ))}
                  </div>
                </div>
              ))}
            </Slider>
          </div>
        </div>
      </div>
    </section>
  );
};

export default TestimonialSection;
