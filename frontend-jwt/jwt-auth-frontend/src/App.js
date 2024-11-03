
import './App.css';
import Dashboard from './Dashboard/Dashboard';
import Login from './Login/Login';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
function App() {
  return (
    <Router>
    <Routes>
        <Route exact path="/" element={<Login />} />
        <Route path="/dashboard" element={<Dashboard />} />
    </Routes>
</Router>
  );
}

export default App;
