import appointmentList from "../data.json";

const Calendar = ({onTournamentChosen}) => {
    return (
        <div>
            <h3 className="mb-3">Saint-Petersburg Beach Volley Tour 2021</h3>
            {appointmentList.tournaments
                .map((appointment) => (
                    <a href="#" className="card" key={'tournament_' + appointment.id} onClick={() => onTournamentChosen(appointment.url)}>
                        <div className="card-body">
                            <h5 className="card-title">{appointment.name}</h5>
                            <p className="card-text">{appointment.categories}</p>
                            <p className="card-text">{appointment.date}</p>
                        </div>
                    </a>
                ))
            }
        </div>
    )
}

export default Calendar