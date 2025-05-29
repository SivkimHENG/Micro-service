import { FormEvent, useState } from "react";
import { useNavigate } from "react-router-dom";
import AuthService from "../services/auth.service";
import { useAuth } from "../context/AuthContext";
import api from "../services/service";


export function Login() {


    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [error, setError] = useState<string>("");
    const navigate = useNavigate();
    const { login } = useAuth();


    const handleLogin = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        setError("");

        try {
            const response = await api.post('/auth/authenticate', JSON.stringify({
                username,
                password
            }));
            console.log(username);
            login(response.data.token);
            navigate("/");

        } catch (err: any) {
            console.log(err);
            setError(err.response?.data || "An error Occurred");
        }

    }

    return (
        <form onSubmit={handleLogin}>
            {error && <div className="error">{error}</div>}
            <div>
                <label>Username:</label>
                <input
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                />
            </div>
            <div>
                <label>Password:</label>
                <input
                    type="text"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
            </div>
            <button type="submit">Login</button>
        </form>


    )


}
