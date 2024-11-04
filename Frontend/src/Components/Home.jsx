import React,{useState,useEffect} from "react";
import axios from "axios";

const Home = () => {

    const [user,setUser] = useState(null);
    useEffect(() => {
        axios.get('http://localhost:8080/api/admin/users/user-info',{withCredentials: true})
            .then(response => {
                setUser(response.data)
            })
            .catch(error => {
                console.error('Error Occured', error);
            })
    }, []);



    return (
 <div>
     <h1>
         Hellllo
     </h1>
 </div>
    );
};

export  default  Home;