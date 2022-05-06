import React, {useState} from "react";
import axios from "axios";

export function DesignerAdd({onAdd}) {
    const [designer, setDesigner] = useState({
        name: "", position: "INTERN"
    });
    const handleNameInputChanged = (e) => setDesigner({...designer, name: e.target.value})
    const handlePositionInputChanged = (e) => setDesigner({...designer, position: e.target.value})


    const onAddDesignerSubmit = () => {
        axios.post('http://localhost:8080/api/v1/designers', {
                name: designer.name,
                position: designer.position,
                specialty: ""
            }
        ).then(
            v => {
                alert("디자이너가 추가되었습니다.");
                onAdd(designer, v.data.id);
            },
            e => {
                alert("서버 장애");
                console.error(e);
            })
    }
    const handleSubmit = (e) => {
        if (designer.name === "" || designer.position === "") {
            alert("입력값을 확인해주세요!")
        } else {
            onAddDesignerSubmit(designer);
        }
    }
    return (
        <>
            <div>
                <h5 className="m-0 p-0"><b>디자이너 추가 Form</b></h5>
            </div>
            <hr/>
            <form>
                <div className="mb-3">
                    <label htmlFor="name" className="form-label">이름</label>
                    <input type="text" className="form-control mb-1" value={designer.name}
                           onChange={handleNameInputChanged}
                           id="name"/>
                </div>
                <div className="mb-3">
                    <label htmlFor="position" className="form-label">직책</label>
                    <select className="form-control mb-1" onChange={(e) => handlePositionInputChanged(e)}>
                        <option value="INTERN">INTERN</option>
                        <option value="DESIGNER">DESIGNER</option>
                        <option value="HEAD_DESIGNER">HEAD_DESIGNER</option>
                    </select>
                </div>


            </form>

            <button className="btn btn-dark col-12" onClick={handleSubmit}>디자이너 추가</button>


        </>
    )
}