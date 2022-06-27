/**
 1. 文件下载
 2. @param params   http://192.168.42.10:80//file/download
 */

import axios from "@/axios/axios";

const url = '/file/download';

export function download(data) {
    let params = new URLSearchParams();
    params.append("fileid", data.fileId);
    const config = {
        method: 'post',
        url: url,
        params,
        headers: {
            // 'xxxx': 'xxxx'
        },
        responseType: 'blob'
    };
    axios(config).then(response => {
        console.log(response)
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', data.fileName);
        document.body.appendChild(link);
        link.click();
    })
}
