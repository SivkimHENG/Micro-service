import { FormEvent, useState } from "react";
import { useNavigate } from "react-router-dom";
import AuthService from "../services/auth.service";

export function Authenticate() {
    const navigate = useNavigate();

    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [loading, setLoading] = useState<boolean>(false);
    const [message, setMessage] = useState<string>("");



    const authenticated = (ev: FormEvent<HTMLFormElement>) => {
        ev.preventDefault();

        setMessage("");
        setLoading(true);


        AuthService.authenticate(username, password)
            .then(() => {
                navigate("/dashboard");
                window.location.reload();
            })
            .catch(error => {
                const responseMessage =
                    (error.response &&
                        error.response.data
                        && error.response.message)
                    || error.message || error.toString();
                setLoading(false);
                setMessage(responseMessage);
            });






    }



}
