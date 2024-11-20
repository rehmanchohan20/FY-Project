import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Payment.css';

function Payment() {
    const [studentId, setStudentId] = useState(1);  // Hardcoding studentId for testing
    const [amount, setAmount] = useState(100000);  // Default amount
    const [transactionId, setTransactionId] = useState(`txn${Date.now()}`);
    const [loading, setLoading] = useState(false);
    const [paymentUrl, setPaymentUrl] = useState("");  // Store payment URL to redirect

    // Hardcode the courseId for testing
    const courseId = 2;  // Replace this with the course ID you want to test with

    useEffect(() => {
        if (!studentId) {
            // Handle case where studentId is not passed or invalid
            alert('Student ID is required!');
        }
    }, [studentId]);

    const handlePayment = async () => {
        if (!studentId) {
            // Ensure the student ID is present before proceeding
            alert('Please log in or select a student.');
            return;
        }

        setLoading(true);

        const paymentRequest = {
            studentId,        // Using hardcoded studentId
            amount,            // Amount to be paid
            transactionId,     // Generated transaction ID
            courseId,          // Using hardcoded courseId
        };

        try {
            // Send payment request to backend
            const response = await axios.post(`http://localhost:8080/api/courses/${courseId}/payments`, paymentRequest, {
                headers: { 'Content-Type': 'application/json' }
            });

            console.log(response.data);  // Log the response to check the URL

            // If the backend response contains the payment URL, use it
            if (response.data && response.data.paymentUrl) {
                const paymentUrl = response.data.paymentUrl;

                // Here, you can redirect to the JazzCash payment gateway directly by encoding the parameters
                const encodedParams = new URLSearchParams({
                    studentId: paymentRequest.studentId,
                    amount: paymentRequest.amount,
                    transactionId: paymentRequest.transactionId,
                    courseId: paymentRequest.courseId,
                    paymentUrl: paymentUrl,  // You can optionally pass paymentUrl here as well
                }).toString();

                // Construct the redirect URL for JazzCash with the encoded parameters
                const redirectUrl = `https://sandbox.jazzcash.com.pk/CustomerPortal/transactionmanagement/merchantform?${encodedParams}`;

                // Redirect the user to the payment gateway
                window.location.href = redirectUrl;
            } else {
                console.error('Payment URL is missing!');
                alert('Failed to get payment URL.');
            }
        } catch (error) {
            console.error('Error creating payment:', error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="payment-container">
            <div className="payment-card">
                <h2 className="payment-heading">Initiate Payment</h2>
                <form onSubmit={(e) => e.preventDefault()} className="payment-form">
                    <div className="form-group">
                        <label>Amount:</label>
                        <input
                            type="number"
                            value={amount}
                            onChange={(e) => setAmount(Number(e.target.value))}
                            className="form-input"
                        />
                    </div>
                    <div className="form-group">
                        <label>Transaction ID:</label>
                        <input
                            type="text"
                            value={transactionId}
                            onChange={(e) => setTransactionId(e.target.value)}
                            className="form-input"
                        />
                    </div>
                    <button
                        type="button"
                        onClick={handlePayment}
                        disabled={loading}
                        className="pay-button"
                    >
                        {loading ? 'Processing Payment...' : 'Proceed to Pay'}
                    </button>
                </form>
            </div>
        </div>
    );
}

export default Payment;





// import React, { useState, useEffect } from 'react';
// import axios from 'axios';
// import './Payment.css';
//
// function Payment({ courseId }) {
//     const [studentID, setSetudentId] = useState(1);
//     const [amount, setAmount] = useState(100000);  // Default amount
//     const [transactionId, setTransactionId] = useState(`txn${Date.now()}`);
//     const [loading, setLoading] = useState(false);
//
//     useEffect(() => {
//         if (!studentId) {
//             // Handle case where studentId is not passed or invalid
//             alert('Student ID is required!');
//         }
//     }, [studentId]);
//
//     const handlePayment = async () => {
//         if (!studentId) {
//             // Ensure the student ID is present before proceeding
//             alert('Please log in or select a student.');
//             return;
//         }
//
//         setLoading(true);
//
//         const paymentRequest = {
//             studentId,        // Dynamic student ID
//             amount,            // Amount to be paid
//             transactionId,     // Generated transaction ID
//             courseId,          // Dynamic course ID
//         };
//
//         try {
//             // Send payment request to backend
//             const response = await axios.post(`http://localhost:8080/api/courses/${courseId}/payments`, paymentRequest, {
//                 headers: { 'Content-Type': 'application/json' }
//             });
//
//             // Assuming backend returns a redirect URL for the payment gateway
//             if (response.data && response.data.redirectUrl) {
//                 window.location.href = response.data.redirectUrl;  // Redirect to payment gateway
//             }
//         } catch (error) {
//             console.error('Error creating payment:', error);
//         } finally {
//             setLoading(false);
//         }
//     };
//
//     return (
//         <div className="payment-container">
//             <div className="payment-card">
//                 <h2 className="payment-heading">Initiate Payment</h2>
//                 <form onSubmit={(e) => e.preventDefault()} className="payment-form">
//                     <div className="form-group">
//                         <label>Amount:</label>
//                         <input
//                             type="number"
//                             value={amount}
//                             onChange={(e) => setAmount(Number(e.target.value))}
//                             className="form-input"
//                         />
//                     </div>
//                     <div className="form-group">
//                         <label>Transaction ID:</label>
//                         <input
//                             type="text"
//                             value={transactionId}
//                             onChange={(e) => setTransactionId(e.target.value)}
//                             className="form-input"
//                         />
//                     </div>
//                     <button
//                         type="button"
//                         onClick={handlePayment}
//                         disabled={loading}
//                         className="pay-button"
//                     >
//                         {loading ? 'Processing Payment...' : 'Proceed to Pay'}
//                     </button>
//                 </form>
//             </div>
//         </div>
//     );
// }
//
// export default Payment;




// import React, { useState } from 'react';
// import axios from 'axios';
// import './Payment.css';
//
// function Payment() {
//     const [courseId, setCourseId] = useState(2);
//     const [studentId, setStudentId] = useState(2);
//     const [amount, setAmount] = useState(100000);
//     const [transactionId, setTransactionId] = useState('txn1234739287492845');
//     const [loading, setLoading] = useState(false);
//
//     const handlePayment = async () => {
//         setLoading(true);
//
//         const paymentRequest = {
//             pp_Version: "1.0",
//             pp_TxnType: "SALE",
//             pp_Language: "EN",
//             pp_MerchantID: "MC134536",
//             pp_Password: "su1t9xb12y",
//             pp_BankID: "TBANK",
//             pp_ProductID: "RETL",
//             pp_TxnRefNo: transactionId,
//             pp_Amount: amount,
//             pp_TxnCurrency: "PKR",
//             pp_TxnDateTime: new Date().toISOString().replace(/[-T:.Z]/g, ""),
//             pp_BillReference: "billRef",
//             pp_Description: "Description of transaction",
//             pp_TxnExpiryDateTime: new Date(Date.now() + 3600000).toISOString().replace(/[-T:.Z]/g, ""),
//             pp_ReturnURL: "http://127.0.0.1:8080/success",
//             ppmpf_1: "1",
//             ppmpf_2: "2",
//             ppmpf_3: "3",
//             ppmpf_4: "4",
//             ppmpf_5: "5"
//         };
//
//         try {
//             // Generate secure hash
//             const hashString = Object.keys(paymentRequest)
//                 .sort()
//                 .map(key => `${key}=${paymentRequest[key]}`)
//                 .join("&");
//
//             const pp_SecureHash = await generateSecureHash(hashString, "22954z3v21");
//             paymentRequest.pp_SecureHash = pp_SecureHash;
//
//             // Create the payment record in your backend
//             await axios.post(`http://localhost:8080/api/courses/${courseId}/payments`, paymentRequest, {
//                 headers: { 'Content-Type': 'application/json' }
//             });
//
//             // Redirect to JazzCash with encoded parameters
//             const encodedParams = new URLSearchParams(paymentRequest).toString();
//             const redirectUrl = `https://sandbox.jazzcash.com.pk/CustomerPortal/transactionmanagement/merchantform?${encodedParams}`;
//             window.location.href = redirectUrl;
//         } catch (error) {
//             console.error('Error creating payment:', error);
//         } finally {
//             setLoading(false);
//         }
//     };
//
//     async function generateSecureHash(data, integritySalt) {
//         try {
//             const encoder = new TextEncoder();
//             const dataBuffer = encoder.encode(data);
//             const saltBuffer = encoder.encode(integritySalt);
//             const key = await crypto.subtle.importKey('raw', saltBuffer, { name: 'HMAC', hash: { name: 'SHA-256' } }, false, ['sign']);
//             const signature = await crypto.subtle.sign('HMAC', key, dataBuffer);
//             return Array.from(new Uint8Array(signature)).map(byte => byte.toString(16).padStart(2, '0')).join('');
//         } catch (error) {
//             console.error('Error generating secure hash:', error);
//             return '';
//         }
//     }
//
//     return (
//         <div className="payment-container">
//             <div className="payment-card">
//                 <h2 className="payment-heading">Initiate Payment</h2>
//                 <form onSubmit={(e) => e.preventDefault()} className="payment-form">
//                     <div className="form-group">
//                         <label>Course ID:</label>
//                         <input
//                             type="number"
//                             value={courseId}
//                             onChange={(e) => setCourseId(Number(e.target.value))}
//                             className="form-input"
//                         />
//                     </div>
//                     <div className="form-group">
//                         <label>Student ID:</label>
//                         <input
//                             type="number"
//                             value={studentId}
//                             onChange={(e) => setStudentId(Number(e.target.value))}
//                             className="form-input"
//                         />
//                     </div>
//                     <div className="form-group">
//                         <label>Amount:</label>
//                         <input
//                             type="number"
//                             value={amount}
//                             onChange={(e) => setAmount(Number(e.target.value))}
//                             className="form-input"
//                         />
//                     </div>
//                     <div className="form-group">
//                         <label>Transaction ID:</label>
//                         <input
//                             type="text"
//                             value={transactionId}
//                             onChange={(e) => setTransactionId(e.target.value)}
//                             className="form-input"
//                         />
//                     </div>
//                     <button
//                         type="button"
//                         onClick={handlePayment}
//                         disabled={loading}
//                         className="pay-button"
//                     >
//                         {loading ? 'Processing Payment...' : 'Proceed to Pay'}
//                     </button>
//                 </form>
//             </div>
//         </div>
//     );
// }
//
// export default Payment;


// import React, { useState } from 'react';
// import axios from 'axios';
// import './Payment.css';
//
// function Payment() {
//     const [courseId, setCourseId] = useState(2);
//     const [studentId, setStudentId] = useState(2);
//     const [amount, setAmount] = useState(100000);
//     const [transactionId, setTransactionId] = useState('txn1234739287492845'); // New transaction ID field
//     const [loading, setLoading] = useState(false);
//
//     const handlePayment = async () => {
//         setLoading(true);
//
//         const paymentRequest = {
//             pp_Version: "1.0",
//             pp_TxnType: "SALE",
//             pp_Language: "EN",
//             pp_MerchantID: "MC134536",
//             pp_Password: "su1t9xb12y",
//             pp_BankID: "TBANK",
//             pp_ProductID: "RETL",
//             pp_TxnRefNo: transactionId, // Using transactionId
//             pp_Amount: amount,
//             pp_TxnCurrency: "PKR",
//             pp_TxnDateTime: new Date().toISOString().replace(/[-T:.Z]/g, ""),
//             pp_BillReference: "billRef",
//             pp_Description: "Description of transaction",
//             pp_TxnExpiryDateTime: new Date(Date.now() + 3600000).toISOString().replace(/[-T:.Z]/g, ""),
//             pp_ReturnURL: "http://127.0.0.1:8080/success",
//             ppmpf_1: "1",
//             ppmpf_2: "2",
//             ppmpf_3: "3",
//             ppmpf_4: "4",
//             ppmpf_5: "5"
//         };
//
//         try {
//             const hashString = Object.keys(paymentRequest)
//                 .sort()
//                 .map(key => `${key}=${paymentRequest[key]}`)
//                 .join("&");
//
//             const pp_SecureHash = await generateSecureHash(hashString, "22954z3v21");
//             paymentRequest.pp_SecureHash = pp_SecureHash;
//
//             const response = await axios.post(`http://localhost:8080/api/courses/${courseId}/payments`, paymentRequest, {
//                 headers: { 'Content-Type': 'application/json' }
//             });
//
//             console.log('Payment Created:', response.data);
//
//             const encodedUrl = `https://sandbox.jazzcash.com.pk/CustomerPortal/transactionmanagement/merchantform/`;
//             window.location.href = encodeURI(encodedUrl);
//         } catch (error) {
//             console.error('Error creating payment:', error);
//         } finally {
//             setLoading(false);
//         }
//     };
//
//     async function generateSecureHash(data, integritySalt) {
//         try {
//             const encoder = new TextEncoder();
//             const dataBuffer = encoder.encode(data);
//             const saltBuffer = encoder.encode(integritySalt);
//             const key = await crypto.subtle.importKey('raw', saltBuffer, { name: 'HMAC', hash: { name: 'SHA-256' } }, false, ['sign']);
//             const signature = await crypto.subtle.sign('HMAC', key, dataBuffer);
//             return Array.from(new Uint8Array(signature)).map(byte => byte.toString(16).padStart(2, '0')).join('');
//         } catch (error) {
//             console.error('Error generating secure hash:', error);
//             return '';
//         }
//     }
//
//     return (
//         <div className="payment-container">
//             <div className="payment-card">
//                 <h2 className="payment-heading">Initiate Payment</h2>
//                 <form onSubmit={(e) => e.preventDefault()} className="payment-form">
//                     <div className="form-group">
//                         <label>Course ID:</label>
//                         <input
//                             type="number"
//                             value={courseId}
//                             onChange={(e) => setCourseId(Number(e.target.value))}
//                             className="form-input"
//                         />
//                     </div>
//                     <div className="form-group">
//                         <label>Student ID:</label>
//                         <input
//                             type="number"
//                             value={studentId}
//                             onChange={(e) => setStudentId(Number(e.target.value))}
//                             className="form-input"
//                         />
//                     </div>
//                     <div className="form-group">
//                         <label>Amount:</label>
//                         <input
//                             type="number"
//                             value={amount}
//                             onChange={(e) => setAmount(Number(e.target.value))}
//                             className="form-input"
//                         />
//                     </div>
//                     <div className="form-group">
//                         <label>Transaction ID:</label>
//                         <input
//                             type="text"
//                             value={transactionId}
//                             onChange={(e) => setTransactionId(e.target.value)}
//                             className="form-input"
//                         />
//                     </div>
//                     <button
//                         type="button"
//                         onClick={handlePayment}
//                         disabled={loading}
//                         className="pay-button"
//                     >
//                         {loading ? 'Processing Payment...' : 'Proceed to Pay'}
//                     </button>
//                 </form>
//             </div>
//         </div>
//     );
// }
//
// export default Payment;




// import React, { useState } from 'react';
// import axios from 'axios';
// import './Payment.css';
//
// function Payment() {
//     const [courseId, setCourseId] = useState(2);
//     const [studentId, setStudentId] = useState(2);
//     const [amount, setAmount] = useState(100000);
//     const [transactionId, setTransactionId] = useState('txn1234739287492845'); // New transaction ID field
//     const [loading, setLoading] = useState(false);
//
//     const handlePayment = async () => {
//         setLoading(true);
//
//         const paymentRequest = {
//             pp_Version: "1.0",
//             pp_TxnType: "SALE",
//             pp_Language: "EN",
//             pp_MerchantID: "MC134536",
//             pp_Password: "su1t9xb12y",
//             pp_BankID: "TBANK",
//             pp_ProductID: "RETL",
//             pp_TxnRefNo: transactionId, // Using transactionId
//             pp_Amount: amount,
//             pp_TxnCurrency: "PKR",
//             pp_TxnDateTime: new Date().toISOString().replace(/[-T:.Z]/g, ""),
//             pp_BillReference: "billRef",
//             pp_Description: "Description of transaction",
//             pp_TxnExpiryDateTime: new Date(Date.now() + 3600000).toISOString().replace(/[-T:.Z]/g, ""),
//             pp_ReturnURL: "http://127.0.0.1:8080/success",
//             ppmpf_1: "1",
//             ppmpf_2: "2",
//             ppmpf_3: "3",
//             ppmpf_4: "4",
//             ppmpf_5: "5"
//         };
//
//         try {
//             const hashString = Object.keys(paymentRequest)
//                 .sort()
//                 .map(key => `${key}=${paymentRequest[key]}`)
//                 .join("&");
//
//             const pp_SecureHash = await generateSecureHash(hashString, "22954z3v21");
//             paymentRequest.pp_SecureHash = pp_SecureHash;
//
//             const response = await axios.post(`http://localhost:8080/api/courses/${courseId}/payments`, paymentRequest, {
//                 headers: { 'Content-Type': 'application/json' }
//             });
//
//             console.log('Payment Created:', response.data);
//
//             const encodedUrl = `https://sandbox.jazzcash.com.pk/CustomerPortal/transactionmanagement/merchantform/?${new URLSearchParams(paymentRequest).toString()}`;
//             window.location.href = encodeURI(encodedUrl);
//         } catch (error) {
//             console.error('Error creating payment:', error);
//         } finally {
//             setLoading(false);
//         }
//     };
//
//     async function generateSecureHash(data, integritySalt) {
//         try {
//             const encoder = new TextEncoder();
//             const dataBuffer = encoder.encode(data);
//             const saltBuffer = encoder.encode(integritySalt);
//             const key = await crypto.subtle.importKey('raw', saltBuffer, { name: 'HMAC', hash: { name: 'SHA-256' } }, false, ['sign']);
//             const signature = await crypto.subtle.sign('HMAC', key, dataBuffer);
//             return Array.from(new Uint8Array(signature)).map(byte => byte.toString(16).padStart(2, '0')).join('');
//         } catch (error) {
//             console.error('Error generating secure hash:', error);
//             return '';
//         }
//     }
//
//     return (
//         <div className="payment-container">
//             <div className="payment-card">
//                 <h2 className="payment-heading">Initiate Payment</h2>
//                 <form onSubmit={(e) => e.preventDefault()} className="payment-form">
//                     <div className="form-group">
//                         <label>Course ID:</label>
//                         <input
//                             type="number"
//                             value={courseId}
//                             onChange={(e) => setCourseId(Number(e.target.value))}
//                             className="form-input"
//                         />
//                     </div>
//                     <div className="form-group">
//                         <label>Student ID:</label>
//                         <input
//                             type="number"
//                             value={studentId}
//                             onChange={(e) => setStudentId(Number(e.target.value))}
//                             className="form-input"
//                         />
//                     </div>
//                     <div className="form-group">
//                         <label>Amount:</label>
//                         <input
//                             type="number"
//                             value={amount}
//                             onChange={(e) => setAmount(Number(e.target.value))}
//                             className="form-input"
//                         />
//                     </div>
//                     <div className="form-group">
//                         <label>Transaction ID:</label>
//                         <input
//                             type="text"
//                             value={transactionId}
//                             onChange={(e) => setTransactionId(e.target.value)}
//                             className="form-input"
//                         />
//                     </div>
//                     <button
//                         type="button"
//                         onClick={handlePayment}
//                         disabled={loading}
//                         className="pay-button"
//                     >
//                         {loading ? 'Processing Payment...' : 'Proceed to Pay'}
//                     </button>
//                 </form>
//             </div>
//         </div>
//     );
// }
//
// export default Payment;
//
//
//
// // import React, { useState } from 'react';
// // import axios from 'axios';
// // import './Payment.css';
// //
// // function Payment() {
// //     const [courseId, setCourseId] = useState(1); // Dummy courseId
// //     const [studentId, setStudentId] = useState(1); // Dummy studentId
// //     const [amount, setAmount] = useState(1000); // Dummy amount
// //     const [loading, setLoading] = useState(false); // Loading state
// //
// //     const handlePayment = async () => {
// //         setLoading(true);
// //
// //         const paymentRequest = {
// //             pp_Version: "1.0",
// //             pp_TxnType: "SALE",
// //             pp_Language: "EN",
// //             pp_MerchantID: "MC134536",
// //             pp_Password: "su1t9xb12y",
// //             pp_BankID: "TBANK",
// //             pp_ProductID: "RETL",
// //             pp_TxnRefNo: `T${new Date().getTime()}`,
// //             pp_Amount: amount,
// //             pp_TxnCurrency: "PKR",
// //             pp_TxnDateTime: new Date().toISOString().replace(/[-T:.Z]/g, ""),
// //             pp_BillReference: "billRef",
// //             pp_Description: "Description of transaction",
// //             pp_TxnExpiryDateTime: new Date(Date.now() + 3600000).toISOString().replace(/[-T:.Z]/g, ""),
// //             pp_ReturnURL: "http://127.0.0.1:8080/success",
// //             ppmpf_1: "1",
// //             ppmpf_2: "2",
// //             ppmpf_3: "3",
// //             ppmpf_4: "4",
// //             ppmpf_5: "5"
// //         };
// //
// //         try {
// //             // Generate Secure Hash (consider server-side)
// //             const hashString = Object.keys(paymentRequest)
// //                 .sort()
// //                 .map(key => `${key}=${paymentRequest[key]}`)
// //                 .join("&");
// //
// //             const pp_SecureHash = await generateSecureHash(hashString, "22954z3v21");
// //             paymentRequest.pp_SecureHash = pp_SecureHash;
// //
// //             const response = await axios.post(`http://localhost:8080/api/courses/${courseId}/payments`, paymentRequest, {
// //                 headers: { 'Content-Type': 'application/json' }
// //             });
// //
// //             console.log('Payment Created:', response.data);
// //
// //             const encodedUrl = `https://sandbox.jazzcash.com.pk/CustomerPortal/transactionmanagement/merchantform/?${new URLSearchParams(paymentRequest).toString()}`;
// //             window.location.href = encodeURI(encodedUrl);
// //         } catch (error) {
// //             console.error('Error creating payment:', error);
// //         } finally {
// //             setLoading(false);
// //         }
// //     };
// //
// //     // Function to generate secure hash (HMAC SHA256)
// //     async function generateSecureHash(data, integritySalt) {
// //         try {
// //             const encoder = new TextEncoder();
// //             const dataBuffer = encoder.encode(data);
// //             const saltBuffer = encoder.encode(integritySalt);
// //             const key = await crypto.subtle.importKey('raw', saltBuffer, { name: 'HMAC', hash: { name: 'SHA-256' } }, false, ['sign']);
// //             const signature = await crypto.subtle.sign('HMAC', key, dataBuffer);
// //             return Array.from(new Uint8Array(signature)).map(byte => byte.toString(16).padStart(2, '0')).join('');
// //         } catch (error) {
// //             console.error('Error generating secure hash:', error);
// //             return '';
// //         }
// //     }
// //
// //     return (
// //         <div className="payment-container">
// //             <div className="payment-card">
// //                 <h2 className="payment-heading">Initiate Payment</h2>
// //                 <form onSubmit={(e) => e.preventDefault()} className="payment-form">
// //                     <div className="form-group">
// //                         <label>Course ID:</label>
// //                         <input
// //                             type="number"
// //                             value={courseId}
// //                             onChange={(e) => setCourseId(Number(e.target.value))}
// //                             className="form-input"
// //                         />
// //                     </div>
// //                     <div className="form-group">
// //                         <label>Student ID:</label>
// //                         <input
// //                             type="number"
// //                             value={studentId}
// //                             onChange={(e) => setStudentId(Number(e.target.value))}
// //                             className="form-input"
// //                         />
// //                     </div>
// //                     <div className="form-group">
// //                         <label>Amount:</label>
// //                         <input
// //                             type="number"
// //                             value={amount}
// //                             onChange={(e) => setAmount(Number(e.target.value))}
// //                             className="form-input"
// //                         />
// //                     </div>
// //                     <button
// //                         type="button"
// //                         onClick={handlePayment}
// //                         disabled={loading}
// //                         className="pay-button"
// //                     >
// //                         {loading ? 'Processing Payment...' : 'Proceed to Pay'}
// //                     </button>
// //                 </form>
// //             </div>
// //         </div>
// //     );
// // }
// //
// // export default Payment;
// //
// //
// // // import React, { useState } from 'react';
// // // import axios from 'axios'; // Import axios
// // // import './Payment.css'; // Import custom CSS for styling
// // //
// // // function Payment() {
// // //     const [courseId, setCourseId] = useState(1); // dummy courseId
// // //     const [studentId, setStudentId] = useState(1); // dummy studentId
// // //     const [amount, setAmount] = useState(1000); // dummy amount
// // //     const [transactionId, setTransactionId] = useState('txn12345'); // dummy transaction ID
// // //     const [loading, setLoading] = useState(false); // to manage loading state
// // //
// // //     const handlePayment = async () => {
// // //         setLoading(true); // Start loading
// // //         // Construct the payment request payload
// // //         const paymentRequest = {
// // //             pp_Version: "1.0",
// // //             pp_TxnType: "SALE", // TxnType for payment
// // //             pp_Language: "EN",
// // //             pp_MerchantID: "MC134536",  // JazzCash Merchant ID
// // //             pp_Password: "su1t9xb12y",  // JazzCash Password
// // //             pp_BankID: "TBANK",
// // //             pp_ProductID: "RETL", // Define product ID
// // //             pp_TxnRefNo: `T${new Date().getTime()}`,  // Generate TxnRefNo from timestamp
// // //             pp_Amount: amount,
// // //             pp_TxnCurrency: "PKR",
// // //             pp_TxnDateTime: new Date().toISOString().replace(/[-T:.Z]/g, ""), // Format datetime
// // //             pp_BillReference: "billRef",  // Reference for bill
// // //             pp_Description: "Description of transaction",  // Description
// // //             pp_TxnExpiryDateTime: new Date(Date.now() + 3600000).toISOString().replace(/[-T:.Z]/g, ""), // 1 hour expiry
// // //             pp_ReturnURL: "http://127.0.0.1:8080/success", // Callback URL after payment success
// // //             ppmpf_1: "1", // Additional parameters
// // //             ppmpf_2: "2",
// // //             ppmpf_3: "3",
// // //             ppmpf_4: "4",
// // //             ppmpf_5: "5"
// // //         };
// // //
// // //         try {
// // //             // Generate Secure Hash using HMAC SHA256
// // //             const hashString = Object.keys(paymentRequest)
// // //                 .sort()
// // //                 .map(key => `${key}=${paymentRequest[key]}`)
// // //                 .join("&");
// // //
// // //             const pp_SecureHash = await generateSecureHash(hashString, "22954z3v21");
// // //
// // //             paymentRequest.pp_SecureHash = pp_SecureHash;
// // //
// // //             // Send payment details to backend
// // //             const response = await axios.post(`http://localhost:8080/api/courses/${courseId}/payments`, paymentRequest, {
// // //                 headers: { 'Content-Type': 'application/json' }
// // //             });
// // //
// // //             console.log('Payment Created:', response.data);
// // //
// // //             // Redirect to JazzCash payment page
// // //             const encodedUrl = `https://sandbox.jazzcash.com.pk/CustomerPortal/transactionmanagement/merchantform/?${new URLSearchParams(paymentRequest).toString()}`;
// // //             window.location.href = encodeURI(encodedUrl);  // Ensure URL is properly encoded
// // //         } catch (error) {
// // //             console.error('Error creating payment:', error);
// // //         } finally {
// // //             setLoading(false); // Stop loading
// // //         }
// // //     };
// // //
// // //     // Function to generate secure hash (HMAC SHA256)
// // //     async function generateSecureHash(data, integritySalt) {
// // //         try {
// // //             const encoder = new TextEncoder();
// // //             const dataBuffer = encoder.encode(data);
// // //             const saltBuffer = encoder.encode(integritySalt);
// // //             const key = await crypto.subtle.importKey('raw', saltBuffer, { name: 'HMAC', hash: { name: 'SHA-256' } }, false, ['sign', 'verify']);
// // //             const signature = await crypto.subtle.sign('HMAC', key, dataBuffer);
// // //             return Array.from(new Uint8Array(signature)).map(byte => byte.toString(16).padStart(2, '0')).join('');
// // //         } catch (error) {
// // //             console.error('Error generating secure hash:', error);
// // //             return ''; // Return empty string in case of error
// // //         }
// // //     }
// // //
// // //     return (
// // //         <div className="payment-container">
// // //             <div className="payment-card">
// // //                 <h2 className="payment-heading">Initiate Payment</h2>
// // //                 <form onSubmit={(e) => e.preventDefault()} className="payment-form">
// // //                     <div className="form-group">
// // //                         <label>Course ID:</label>
// // //                         <input
// // //                             type="number"
// // //                             value={courseId}
// // //                             onChange={(e) => setCourseId(Number(e.target.value))}
// // //                             className="form-input"
// // //                         />
// // //                     </div>
// // //                     <div className="form-group">
// // //                         <label>Student ID:</label>
// // //                         <input
// // //                             type="number"
// // //                             value={studentId}
// // //                             onChange={(e) => setStudentId(Number(e.target.value))}
// // //                             className="form-input"
// // //                         />
// // //                     </div>
// // //                     <div className="form-group">
// // //                         <label>Amount:</label>
// // //                         <input
// // //                             type="number"
// // //                             value={amount}
// // //                             onChange={(e) => setAmount(Number(e.target.value))}
// // //                             className="form-input"
// // //                         />
// // //                     </div>
// // //                     <div className="form-group">
// // //                         <label>Transaction ID:</label>
// // //                         <input
// // //                             type="text"
// // //                             value={transactionId}
// // //                             onChange={(e) => setTransactionId(e.target.value)}
// // //                             className="form-input"
// // //                         />
// // //                     </div>
// // //                     <button
// // //                         type="button"
// // //                         onClick={handlePayment}
// // //                         disabled={loading}
// // //                         className="pay-button"
// // //                     >
// // //                         {loading ? 'Processing Payment...' : 'Proceed to Pay'}
// // //                     </button>
// // //                 </form>
// // //             </div>
// // //         </div>
// // //     );
// // // }
// // //
// // // export default Payment;