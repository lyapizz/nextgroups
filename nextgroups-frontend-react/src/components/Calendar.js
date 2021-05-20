import appointmentList from "../data.json";
import moment from 'moment';

const Calendar = ({onTournamentChosen}) => {
    return (
        <div>
            <h3 className="mb-3">Saint-Petersburg Beach Volley Tour 2021</h3>
            {appointmentList.tournaments
                .filter((tournament) => {
                    let arrayOfStrings = tournament.date.split('-');
                    let parsedDate = arrayOfStrings[arrayOfStrings.length - 1];
                    return new Date() <= moment(parsedDate, "DD.MM.YYYY").toDate()
                })
                .map((tournament) => (
                    <a href="#" className="card" key={'tournament_' + tournament.id} onClick={() => onTournamentChosen(tournament.url)}>
                        <div className="card-body">
                            <h5 className="card-title">{tournament.name}</h5>
                            <p className="card-text">{tournament.categories}</p>
                            <p className="card-text">{tournament.date}</p>
                        </div>
                    </a>
                ))
            }
        </div>
    )
}

export default Calendar