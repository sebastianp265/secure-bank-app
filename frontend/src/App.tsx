import './App.css'
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {PageNotFound} from "./pages/not_found/PageNotFound.tsx";
import {Login} from "./pages/login/Login.tsx";
import {Home} from "./pages/home/Home.tsx";
import {TransferHistory} from "./pages/transfers/history/TransferHistory.tsx";
import {TransferSend} from "./pages/transfers/send/TransferSend.tsx";

function App() {

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Login/>} />
                <Route path="/home" element={<Home/>} />
                <Route path="/transfers/history/:accountNumber" element={<TransferHistory/>} />
                <Route path="/transfers/send" element={<TransferSend/>} />
                <Route path="*" element={<PageNotFound/>} />
            </Routes>
        </BrowserRouter>
    )
}

export default App
