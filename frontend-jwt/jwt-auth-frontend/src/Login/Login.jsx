import React, { useState } from 'react'
import "./Login.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Login = () => {
    const[username,setUsername]=useState("");
    const[password,setPassword]=useState("");
    const navigate=useNavigate();

     const handleLogin=async(e)=>{
         e.preventDefault();
         try {
           const response= await axios.post("http://localhost:8080/login",{
                username,
                password
                 
            })
            const token = response.data.token;
            localStorage.setItem("token",token)
            alert("Login Success");
            navigate("/dashboard")
         } catch (error) {
            console.error("Login failed", error);
            alert("Login failed, please try again.");
         }
     }
  return (
    <><h2>Login</h2>
    <form onSubmit={handleLogin}>
          <input
              type="text"
              placeholder="Username"
              value={username}
              onChange={(e) => setUsername(e.target.value)} /> <br /> <br />
          <input
              type="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)} /> <br /> <br />
          <button type="submit">Login</button> 
      </form></>
    
  )
}

export default Login