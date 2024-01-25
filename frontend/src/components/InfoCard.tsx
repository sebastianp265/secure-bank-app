type InfoPart = {
    key: string,
    value: string,
    showDetails: (key: string) => void
}

interface InfoCardProps {
    content: Array<InfoPart>
}

export const InfoCard = ({content}: InfoCardProps) => {
    return (
        <div className="flex flex-col border-black border-2 rounded-xl p-4 w-full">
            <div className="flex flex-col">
                {
                    content.map(infoPart => {
                        return (
                            <div key={infoPart.key} className="flex flex-row">
                                <div className="font-bold">{infoPart.key}: </div>
                                <div className="ml-1">{infoPart.value}</div>
                                {
                                    infoPart.value?.includes("*") &&
                                    <button onClick={() => infoPart.showDetails(infoPart.key)}
                                            className="ml-4 text-violet-500 hover:text-violet-700">Show details</button>
                                }
                            </div>
                        )
                    })
                }
            </div>
        </div>
    )
}