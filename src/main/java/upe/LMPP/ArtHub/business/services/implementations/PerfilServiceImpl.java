package upe.LMPP.ArtHub.business.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import upe.LMPP.ArtHub.business.services.interfaces.PerfilService;
import upe.LMPP.ArtHub.infra.entities.Perfil;
import upe.LMPP.ArtHub.infra.entities.Usuario;
import upe.LMPP.ArtHub.infra.exceptions.perfilExceptions.PerfilInexistenteException;
import upe.LMPP.ArtHub.infra.exceptions.usuarioExceptions.UsuarioInexistenteException;
import upe.LMPP.ArtHub.infra.repositories.PerfilRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PerfilServiceImpl implements PerfilService {

    @Autowired
    PerfilRepository perfilRepository;

    @Override
    public Perfil criarPerfil(Usuario usuario) {
        Perfil novoPerfil = Perfil
                .builder()
                .usuario(usuario)
                .build();

        return perfilRepository.save(novoPerfil);
    }

    @Override
    public boolean seguirUsuario(Integer usuario, Integer seguindo) {
        Perfil perfilUsuario = perfilRepository
                .findById(usuario)
                .orElseThrow(PerfilInexistenteException::new);

        Perfil perfilSeguindo = perfilRepository
                .findById(seguindo)
                .orElseThrow(PerfilInexistenteException::new);

        if (perfilUsuario.getSeguindo().contains(perfilSeguindo.getUsuario().getPerfil())){
            return false;
        }

        perfilUsuario.getSeguindo().add(perfilSeguindo.getUsuario().getPerfil());
        perfilSeguindo.getSeguindo().add(perfilUsuario.getUsuario().getPerfil());
        perfilRepository.save(perfilSeguindo);
        perfilRepository.save(perfilUsuario);

        return true;
    }

    // email, senha, perfil, banner, nome, telefone, bio
    @Override
    public Perfil atualizarPerfil(Perfil perfil) {
        if (!perfilRepository.existsById(perfil.getId())) {
            throw new PerfilInexistenteException();
        }

        return perfilRepository.save(perfil);
    }

    @Override
    public void removerPerfil(Integer idUsuario) {
        Optional<Perfil> perfil = perfilRepository.findByIdUsuario(idUsuario);

        if (perfil.isEmpty()){
            throw new UsuarioInexistenteException();
        }

        perfilRepository.delete(perfil.get());
    }

    @Override
    public Perfil obterPerfil(Integer id) {
        return perfilRepository
                .findById(id)
                .orElseThrow(PerfilInexistenteException::new);
    }

    @Override
    public List<Usuario> obterSeguidos(Integer idUsuario) {
        Optional<Perfil> perfil = perfilRepository.findByIdUsuario(idUsuario);

        if (perfil.isEmpty()){
            throw new UsuarioInexistenteException();
        }

        return perfil.get().getSeguindo().stream().map(Perfil::getUsuario).toList();
    }

    @Override
    public List<Usuario> obterSeguidores(Integer idUsuario) {
        Optional<Perfil> perfil = perfilRepository.findByIdUsuario(idUsuario);

        if (perfil.isEmpty()){
            throw new UsuarioInexistenteException();
        }

        return perfil.get().getSeguidores().stream().map(Perfil::getUsuario).toList();
    }
}
