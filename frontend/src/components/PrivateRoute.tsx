import { ReactNode, useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import { Navigate } from "react-router-dom";


interface PrivateRouteProps {
    children: ReactNode;
}



const PrivateRoute: React.FC<PrivateRouteProps> = ({ children }) => {

    const { isAuthenticated, loading } = useContext(AuthContext);

    if (loading) {
        return <div>Loading...</div>;
    }


    if (!isAuthenticated) {
        return <Navigate to="/authenticate" />
    }

    return <>{children}</>


}

export default PrivateRoute;
