import React from "react";
import axios from "axios";

export function Menu(props) {
    const id = props.id;
    const name = props.name;
    const price = props.price;

    const handleSubmit = (e) => {
        axios({
                url:`http://localhost:8080/api/v1/menus/`+id,
                method : 'delete'
            }

        ).then(
            v => {alert("시술메뉴 정보가 삭제되었습니다.")
                props.onRemove(id)},

            e => {
                alert("서버 장애");
                console.error(e);
            })
    }

    return <>

        <div className="col">
            <div className="row text-muted">{id}</div>
            <div className="row">{name}</div>
        </div>
        <div className="col text-center position">{price}</div>
        <button className="btn btn-small btn-outline-dark" onClick={handleSubmit}>삭제</button>
    </>
}