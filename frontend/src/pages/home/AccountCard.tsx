import {AccountGetDTO} from "../../api/domainApi.ts";
import {Button} from "../../components/Button.tsx";
import {useNavigate} from "react-router-dom";

interface AccountCardProps {
    account: AccountGetDTO
}

export const AccountCard = ({account}: AccountCardProps) => {
    const navigate = useNavigate()

    return (
        <div className="text-left">
            <div key={account.accountNumber}>
                <span>{account.accountNumber}</span>
            </div>
            <div>
                <span>Balance: </span>
                <span>{account.balance}</span>
            </div>
            <div className="text-right space-x-1 m-2">
                <Button onClick={() => navigate(`/transfers/history/${account.accountNumber}`)}>History</Button>
                <Button onClick={() => navigate("/transfers/send")}>Transfer</Button>
            </div>
        </div>
    )
}