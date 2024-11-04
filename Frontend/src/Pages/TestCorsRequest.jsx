import axios from 'axios';

function TestCorsRequest() {
    const testCors = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/test/cors', {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            console.log("Response from backend:", response.data);
        } catch (error) {
            console.error("CORS test failed:", error);
        }
    };

    return (
        <div>
            <button onClick={testCors}>Test CORS</button>
        </div>
    );
}

export default TestCorsRequest;
