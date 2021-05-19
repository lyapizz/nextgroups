import React from 'react'
import Groups from './Groups';

const GroupResults = ({groupResultsResponse}) => {

    return (
        <div className="accordion accordion-flush" id="accordionExample">
            {groupResultsResponse.map((groupResult, index) => (
                <div className="accordion-item" key={'item_' + index}>
                    <h3 className="accordion-header" id={'heading_' + index}>
                        <button className="accordion-button collapsed" type="button" data-bs-toggle="collapse" aria-expanded="false"
                                data-bs-target={'#collapse_' + index}
                                aria-controls={'collapse_' + index}>
                            Category: {groupResult.category}
                        </button>
                    </h3>
                    <div id={'collapse_' + index}
                         className="accordion-collapse collapse" data-bs-parent="#accordionExample"
                         aria-labelledby={'heading_' + index}>
                        <div className="accordion-body" id={"groups-body_" + index}>
                            {groupResult.problem != null &&
                            <div className="alert alert-warning" role="alert">
                                {groupResult.problem}
                            </div>
                            }
                            <Groups groups={groupResult.groups}/>
                        </div>
                    </div>
                </div>
            ))}
        </div>
    )
};

export default GroupResults