import { FormEvent, useState } from "react"


export const Register: React.FC = () => {

    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [email, setEmail] = useState<string>("");
    const [message, setMessage] = useState<string>("");
    const [success, setSuccess] = useState<boolean>(false);


    const handleRegister = (ev: FormEvent<HTMLFormElement>) => {
        ev.preventDefault();
        setMessage("");
        setSuccess(false);
    }


    return (


    )


}
