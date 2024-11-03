import React, { useEffect, useState } from 'react'
import axios from "../axios/axiosconfig"; // Use the exact name after renaming


const Dashboard = () => {
    const [data, setData] = useState(null);
    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get("http://localhost:8080/students");
                setData(response.data);
            } catch (error) {
                console.error("Failed to fetch protected resource:", error);
            }
        };

        fetchData();
    }, []);
  return (
    <div>
    <h2>Dashboard</h2>
    {data ? <pre>{JSON.stringify(data, null, 2)}</pre> : <p>Loading...</p>}
</div>
);
}

export default Dashboard