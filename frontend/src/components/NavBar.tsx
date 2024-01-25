import {useNavigate} from "react-router-dom";
import {Button} from "./Button.tsx";
import {securityApi} from "../api/securityApi.ts";

export const NavBar = () => {
    const navigate = useNavigate()

    const handleLogout = () => {
        securityApi.logout()
            .then(() => navigate("/"))

    }

    return (
        <div className="pl-4 w-full pr-4 flex bg-white flex-row justify-between items-center h-16">
            <span className="text-xl font-bold">Bank App</span>
            <Button onClick={() => navigate("/home")} className="ml-auto mr-2">Home</Button>
            <Button onClick={() => navigate("/user")}>User</Button>
            <Button onClick={handleLogout}>Logout</Button>
        </div>
    )

}